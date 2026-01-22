package com.ezlevup.ganbyeong24.presentation.screens.caregiver

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongTextField
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import org.koin.androidx.compose.koinViewModel

/**
 * 간병사 등록 화면
 *
 * 간병사가 자신의 정보를 등록할 수 있는 화면입니다.
 *
 * @param viewModel 간병사 등록 ViewModel
 * @param onNavigateBack 뒤로가기 콜백
 * @param onSuccess 등록 성공 시 호출되는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaregiverRegistrationScreen(
        viewModel: CaregiverRegistrationViewModel = koinViewModel(),
        onNavigateBack: () -> Unit = {},
        onSuccess: () -> Unit
) {
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current

        // 갤러리에서 이미지 선택
        val imagePickerLauncher =
                rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? -> uri?.let { viewModel.onPhotoSelected(it, context) } }

        // 등록 성공 시 ResultScreen으로 이동
        LaunchedEffect(state.isSuccess) {
                if (state.isSuccess) {
                        onSuccess()
                }
        }

        Scaffold(
                topBar = {
                        TopAppBar(
                                title = { Text("간병사 등록") },
                                navigationIcon = {
                                        IconButton(onClick = onNavigateBack) {
                                                Icon(
                                                        Icons.Default.ArrowBack,
                                                        contentDescription = "뒤로가기"
                                                )
                                        }
                                }
                        )
                }
        ) { padding ->
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .padding(padding)
                                        .verticalScroll(rememberScrollState())
                                        .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        // 프로필 사진 선택
                        Column(
                                modifier = Modifier.padding(bottom = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Text(
                                        text = "프로필 사진",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Box(
                                        modifier =
                                                Modifier.size(120.dp)
                                                        .clip(CircleShape)
                                                        .border(
                                                                2.dp,
                                                                MaterialTheme.colorScheme.primary,
                                                                CircleShape
                                                        )
                                                        .clickable {
                                                                imagePickerLauncher.launch(
                                                                        "image/*"
                                                                )
                                                        },
                                        contentAlignment = Alignment.Center
                                ) {
                                        if (state.photoUri != null) {
                                                Image(
                                                        painter =
                                                                rememberAsyncImagePainter(
                                                                        state.photoUri
                                                                ),
                                                        contentDescription = "프로필 사진",
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentScale = ContentScale.Crop
                                                )
                                        } else {
                                                Icon(
                                                        imageVector = Icons.Default.Person,
                                                        contentDescription = "사진 추가",
                                                        modifier = Modifier.size(60.dp),
                                                        tint = MaterialTheme.colorScheme.primary
                                                )
                                        }
                                }

                                Text(
                                        text = "사진을 선택하려면 클릭하세요",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 8.dp)
                                )
                        }

                        // 이름
                        GanbyeongTextField(
                                value = state.name,
                                onValueChange = viewModel::onNameChange,
                                label = "이름 *",
                                placeholder = "예: 김영희",
                                isError = state.nameError != null,
                                errorMessage = state.nameError,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 성별
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                Text(
                                        text = "성별 *",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color =
                                                if (state.genderError != null)
                                                        MaterialTheme.colorScheme.error
                                                else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                        // 남성 버튼
                                        OutlinedButton(
                                                onClick = { viewModel.onGenderChange("남성") },
                                                modifier = Modifier.weight(1f),
                                                colors =
                                                        ButtonDefaults.outlinedButtonColors(
                                                                containerColor =
                                                                        if (state.gender == "남성")
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primaryContainer
                                                                        else Color.Transparent,
                                                                contentColor =
                                                                        if (state.gender == "남성")
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onPrimaryContainer
                                                                        else
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurface
                                                        ),
                                                border =
                                                        BorderStroke(
                                                                1.dp,
                                                                if (state.gender == "남성")
                                                                        MaterialTheme.colorScheme
                                                                                .primary
                                                                else
                                                                        MaterialTheme.colorScheme
                                                                                .outline
                                                        )
                                        ) { Text("남성") }

                                        // 여성 버튼
                                        OutlinedButton(
                                                onClick = { viewModel.onGenderChange("여성") },
                                                modifier = Modifier.weight(1f),
                                                colors =
                                                        ButtonDefaults.outlinedButtonColors(
                                                                containerColor =
                                                                        if (state.gender == "여성")
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .primaryContainer
                                                                        else Color.Transparent,
                                                                contentColor =
                                                                        if (state.gender == "여성")
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onPrimaryContainer
                                                                        else
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurface
                                                        ),
                                                border =
                                                        BorderStroke(
                                                                1.dp,
                                                                if (state.gender == "여성")
                                                                        MaterialTheme.colorScheme
                                                                                .primary
                                                                else
                                                                        MaterialTheme.colorScheme
                                                                                .outline
                                                        )
                                        ) { Text("여성") }
                                }

                                // 에러 메시지
                                if (state.genderError != null) {
                                        Text(
                                                text = state.genderError!!,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier =
                                                        Modifier.padding(top = 4.dp, start = 16.dp)
                                        )
                                }
                        }

                        // 경력 (드롭다운)
                        var experienceExpanded by remember { mutableStateOf(false) }
                        val experienceOptions = listOf("1년 미만", "1~3년", "3~5년", "5~10년", "10년 이상")

                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                ExposedDropdownMenuBox(
                                        expanded = experienceExpanded,
                                        onExpandedChange = { experienceExpanded = it }
                                ) {
                                        OutlinedTextField(
                                                value = state.experience,
                                                onValueChange = {},
                                                readOnly = true,
                                                label = { Text("경력 *") },
                                                placeholder = { Text("선택해주세요") },
                                                trailingIcon = {
                                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                                                expanded = experienceExpanded
                                                        )
                                                },
                                                isError = state.experienceError != null,
                                                colors =
                                                        ExposedDropdownMenuDefaults
                                                                .outlinedTextFieldColors(),
                                                modifier = Modifier.menuAnchor().fillMaxWidth()
                                        )

                                        ExposedDropdownMenu(
                                                expanded = experienceExpanded,
                                                onDismissRequest = { experienceExpanded = false }
                                        ) {
                                                experienceOptions.forEach { option ->
                                                        DropdownMenuItem(
                                                                text = { Text(option) },
                                                                onClick = {
                                                                        viewModel
                                                                                .onExperienceChange(
                                                                                        option
                                                                                )
                                                                        experienceExpanded = false
                                                                },
                                                                contentPadding =
                                                                        ExposedDropdownMenuDefaults
                                                                                .ItemContentPadding
                                                        )
                                                }
                                        }
                                }

                                // 에러 메시지
                                if (state.experienceError != null) {
                                        Text(
                                                text = state.experienceError!!,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier =
                                                        Modifier.padding(top = 4.dp, start = 16.dp)
                                        )
                                }
                        }

                        // 자격증 (칩 선택)
                        val certificateOptions =
                                listOf("요양보호사", "간호사", "간호조무사", "물리치료사", "사회복지사", "기타")

                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                Text(
                                        text = "자격증 * (복수 선택 가능)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color =
                                                if (state.certificatesError != null)
                                                        MaterialTheme.colorScheme.error
                                                else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                )

                                // 칩 그리드
                                FlowRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                        certificateOptions.forEach { certificate ->
                                                FilterChip(
                                                        selected =
                                                                state.certificates.contains(
                                                                        certificate
                                                                ),
                                                        onClick = {
                                                                viewModel.onCertificateToggle(
                                                                        certificate
                                                                )
                                                        },
                                                        label = { Text(certificate) },
                                                        leadingIcon =
                                                                if (state.certificates.contains(
                                                                                certificate
                                                                        )
                                                                ) {
                                                                        {
                                                                                Icon(
                                                                                        imageVector =
                                                                                                Icons.Default
                                                                                                        .Check,
                                                                                        contentDescription =
                                                                                                "선택됨",
                                                                                        modifier =
                                                                                                Modifier.size(
                                                                                                        18.dp
                                                                                                )
                                                                                )
                                                                        }
                                                                } else null
                                                )
                                        }
                                }

                                // 선택된 자격증 표시
                                if (state.certificates.isNotEmpty()) {
                                        Text(
                                                text =
                                                        "선택됨: ${state.certificates.joinToString(", ")}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(top = 8.dp)
                                        )
                                }

                                // 에러 메시지
                                if (state.certificatesError != null) {
                                        Text(
                                                text = state.certificatesError!!,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier =
                                                        Modifier.padding(top = 4.dp, start = 16.dp)
                                        )
                                }
                        }

                        // 가능 지역 (칩 선택)
                        val regionOptions =
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
                                )

                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                Text(
                                        text = "가능 지역 * (복수 선택 가능)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color =
                                                if (state.availableRegionsError != null)
                                                        MaterialTheme.colorScheme.error
                                                else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                )

                                // 칩 그리드
                                FlowRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                        regionOptions.forEach { region ->
                                                FilterChip(
                                                        selected =
                                                                state.availableRegions.contains(
                                                                        region
                                                                ),
                                                        onClick = {
                                                                viewModel.onRegionToggle(region)
                                                        },
                                                        label = { Text(region) },
                                                        leadingIcon =
                                                                if (state.availableRegions.contains(
                                                                                region
                                                                        )
                                                                ) {
                                                                        {
                                                                                Icon(
                                                                                        imageVector =
                                                                                                Icons.Default
                                                                                                        .Check,
                                                                                        contentDescription =
                                                                                                "선택됨",
                                                                                        modifier =
                                                                                                Modifier.size(
                                                                                                        18.dp
                                                                                                )
                                                                                )
                                                                        }
                                                                } else null
                                                )
                                        }
                                }

                                // 선택된 지역 표시
                                if (state.availableRegions.isNotEmpty()) {
                                        Text(
                                                text =
                                                        "선택됨: ${state.availableRegions.joinToString(", ")}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(top = 8.dp)
                                        )
                                }

                                // 에러 메시지
                                if (state.availableRegionsError != null) {
                                        Text(
                                                text = state.availableRegionsError!!,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.error,
                                                modifier =
                                                        Modifier.padding(top = 4.dp, start = 16.dp)
                                        )
                                }
                        }

                        // 연락처
                        GanbyeongTextField(
                                value = state.phoneNumber,
                                onValueChange = viewModel::onPhoneNumberChange,
                                label = "연락처 *",
                                placeholder = "010-1111-2222",
                                isError = state.phoneNumberError != null,
                                errorMessage = state.phoneNumberError,
                                keyboardOptions =
                                        KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.padding(bottom = 32.dp)
                        )

                        // 등록 버튼
                        GanbyeongButton(
                                text = "등록하기",
                                onClick = viewModel::registerCaregiver,
                                isLoading = state.isLoading
                        )
                }
        }

        // 에러 다이얼로그
        if (state.errorMessage != null) {
                AlertDialog(
                        onDismissRequest = viewModel::clearError,
                        title = { Text("오류") },
                        text = { Text(state.errorMessage!!) },
                        confirmButton = {
                                TextButton(onClick = viewModel::clearError) { Text("확인") }
                        }
                )
        }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun CaregiverRegistrationScreenPreview() {
        GanbyeongTheme {
                CaregiverRegistrationScreenContent(
                        state = CaregiverRegistrationState(),
                        onNameChange = {},
                        onExperienceChange = {},
                        onCertificatesChange = {},
                        onAvailableRegionsChange = {},
                        onPhoneNumberChange = {},
                        onSubmit = {},
                        onNavigateBack = {},
                        onClearError = {}
                )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CaregiverRegistrationScreenContent(
        state: CaregiverRegistrationState,
        onNameChange: (String) -> Unit,
        onExperienceChange: (String) -> Unit,
        onCertificatesChange: (String) -> Unit,
        onAvailableRegionsChange: (String) -> Unit,
        onPhoneNumberChange: (String) -> Unit,
        onSubmit: () -> Unit,
        onNavigateBack: () -> Unit,
        onClearError: () -> Unit
) {
        Scaffold(
                topBar = {
                        TopAppBar(
                                title = { Text("간병사 등록") },
                                navigationIcon = {
                                        IconButton(onClick = onNavigateBack) {
                                                Icon(
                                                        Icons.Default.ArrowBack,
                                                        contentDescription = "뒤로가기"
                                                )
                                        }
                                }
                        )
                }
        ) { padding ->
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .padding(padding)
                                        .verticalScroll(rememberScrollState())
                                        .padding(24.dp)
                ) {
                        GanbyeongTextField(
                                value = state.name,
                                onValueChange = onNameChange,
                                label = "이름 *",
                                placeholder = "예: 김영희",
                                isError = state.nameError != null,
                                errorMessage = state.nameError,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        GanbyeongTextField(
                                value = state.experience,
                                onValueChange = onExperienceChange,
                                label = "경력 *",
                                placeholder = "예: 5년",
                                isError = state.experienceError != null,
                                errorMessage = state.experienceError,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        GanbyeongTextField(
                                value = state.certificates.toString(),
                                onValueChange = onCertificatesChange,
                                label = "자격증 *",
                                placeholder = "예: 요양보호사",
                                isError = state.certificatesError != null,
                                errorMessage = state.certificatesError,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        GanbyeongTextField(
                                value = state.availableRegions.toString(),
                                onValueChange = onAvailableRegionsChange,
                                label = "가능 지역 *",
                                placeholder = "예: 서울, 경기",
                                isError = state.availableRegionsError != null,
                                errorMessage = state.availableRegionsError,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        GanbyeongTextField(
                                value = state.phoneNumber,
                                onValueChange = onPhoneNumberChange,
                                label = "연락처 *",
                                placeholder = "010-1111-2222",
                                isError = state.phoneNumberError != null,
                                errorMessage = state.phoneNumberError,
                                keyboardOptions =
                                        KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.padding(bottom = 32.dp)
                        )

                        GanbyeongButton(
                                text = "등록하기",
                                onClick = onSubmit,
                                isLoading = state.isLoading
                        )
                }
        }

        if (state.errorMessage != null) {
                AlertDialog(
                        onDismissRequest = onClearError,
                        title = { Text("오류") },
                        text = { Text(state.errorMessage) },
                        confirmButton = { TextButton(onClick = onClearError) { Text("확인") } }
                )
        }
}
