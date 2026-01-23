package com.ezlevup.ganbyeong24.presentation.screens.admin.caregiver

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.data.model.Caregiver
import org.koin.androidx.compose.koinViewModel

/**
 * 관리자용 간병사 목록 화면
 *
 * 모든 간병사를 조회하고 지역/자격증/경력별로 필터링할 수 있습니다.
 *
 * @param onNavigateBack 뒤로가기 콜백
 * @param viewModel ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCaregiverListScreen(
        onNavigateBack: () -> Unit,
        viewModel: AdminCaregiverListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병사 관리") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "뒤로 가기"
                                )
                            }
                        }
                )
            }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // 필터 섹션
            FilterSection(
                    selectedRegion = state.selectedRegion,
                    selectedCertificate = state.selectedCertificate,
                    selectedExperience = state.selectedExperience,
                    onRegionSelected = { viewModel.setRegionFilter(it) },
                    onCertificateSelected = { viewModel.setCertificateFilter(it) },
                    onExperienceSelected = { viewModel.setExperienceFilter(it) },
                    onClearFilters = { viewModel.clearFilters() },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Divider()

            // 목록 내용
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    state.errorMessage != null -> {
                        ErrorContent(
                                errorMessage = state.errorMessage ?: "알 수 없는 오류",
                                onRetry = { viewModel.loadAllCaregivers() }
                        )
                    }
                    state.filteredCaregivers.isEmpty() -> {
                        EmptyContent()
                    }
                    else -> {
                        LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.filteredCaregivers) { caregiver ->
                                CaregiverItem(caregiver = caregiver)
                            }
                        }
                    }
                }
            }
        }
    }
}

/** 필터 섹션 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
        selectedRegion: String?,
        selectedCertificate: String?,
        selectedExperience: String?,
        onRegionSelected: (String?) -> Unit,
        onCertificateSelected: (String?) -> Unit,
        onExperienceSelected: (String?) -> Unit,
        onClearFilters: () -> Unit,
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text("필터", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            TextButton(onClick = onClearFilters) { Text("초기화") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 지역 필터
        FilterDropdown(
                label = "지역",
                selectedValue = selectedRegion,
                options =
                        listOf(
                                "서울",
                                "경기",
                                "인천",
                                "부산",
                                "대구",
                                "대전",
                                "광주",
                                "울산",
                                "세종",
                                "강원",
                                "충북",
                                "충남",
                                "전북",
                                "전남",
                                "경북",
                                "경남",
                                "제주"
                        ),
                onValueSelected = onRegionSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 자격증 필터
        FilterDropdown(
                label = "자격증",
                selectedValue = selectedCertificate,
                options = listOf("요양보호사", "간호사", "간호조무사", "물리치료사", "사회복지사", "기타"),
                onValueSelected = onCertificateSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 경력 필터
        FilterDropdown(
                label = "경력",
                selectedValue = selectedExperience,
                options = listOf("1년 미만", "1~3년", "3~5년", "5~10년", "10년 이상"),
                onValueSelected = onExperienceSelected
        )
    }
}

/** 필터 드롭다운 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
        label: String,
        selectedValue: String?,
        options: List<String>,
        onValueSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
                value = selectedValue ?: "전체",
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                    text = { Text("전체") },
                    onClick = {
                        onValueSelected(null)
                        expanded = false
                    }
            )
            options.forEach { option ->
                DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueSelected(option)
                            expanded = false
                        }
                )
            }
        }
    }
}

/** 간병사 카드 */
@Composable
fun CaregiverItem(caregiver: Caregiver) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // 프로필 사진
            ProfileImage(photoBase64 = caregiver.photoBase64, modifier = Modifier.size(80.dp))

            Spacer(modifier = Modifier.width(16.dp))

            // 정보
            Column(modifier = Modifier.weight(1f)) {
                // 이름 + 성별
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                            text = caregiver.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                            text = caregiver.gender,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 경력
                Text(
                        text = "경력: ${caregiver.experience}",
                        style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 연락처
                Text(
                        text = "연락처: ${caregiver.phoneNumber}",
                        style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 자격증
                if (caregiver.certificates.isNotEmpty()) {
                    Text(
                            text = "자격증",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ChipGroup(items = caregiver.certificates)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // 가능 지역
                if (caregiver.availableRegions.isNotEmpty()) {
                    Text(
                            text = "가능 지역",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ChipGroup(items = caregiver.availableRegions)
                }
            }
        }
    }
}

/** 프로필 이미지 */
@Composable
fun ProfileImage(photoBase64: String?, modifier: Modifier = Modifier) {
    // Base64 디코딩을 remember로 캐싱
    val bitmap =
            remember(photoBase64) {
                if (photoBase64 != null && photoBase64.isNotEmpty()) {
                    try {
                        val imageBytes = Base64.decode(photoBase64, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    } catch (e: Exception) {
                        null
                    }
                } else {
                    null
                }
            }

    // 디코딩 결과에 따라 UI 표시
    if (bitmap != null) {
        Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "프로필 사진",
                modifier = modifier.clip(CircleShape),
                contentScale = ContentScale.Crop
        )
    } else {
        DefaultProfileIcon(modifier)
    }
}

/** 기본 프로필 아이콘 */
@Composable
fun DefaultProfileIcon(modifier: Modifier = Modifier) {
    Surface(
            modifier = modifier.clip(CircleShape),
            color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "기본 프로필",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

/** 칩 그룹 */
@Composable
fun ChipGroup(items: List<String>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items.take(3).forEach { item ->
            SuggestionChip(
                    onClick = {},
                    label = { Text(item, style = MaterialTheme.typography.labelSmall) }
            )
        }
        if (items.size > 3) {
            SuggestionChip(
                    onClick = {},
                    label = {
                        Text("+${items.size - 3}", style = MaterialTheme.typography.labelSmall)
                    }
            )
        }
    }
}

/** 에러 화면 */
@Composable
fun ErrorContent(errorMessage: String, onRetry: () -> Unit) {
    Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = "오류 발생",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = errorMessage, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) { Text("다시 시도") }
    }
}

/** 빈 목록 화면 */
@Composable
fun EmptyContent() {
    Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(text = "간병사가 없습니다", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                text = "등록된 간병사가 없거나 필터 조건에 맞는 간병사가 없습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
