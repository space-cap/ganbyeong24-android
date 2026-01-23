package com.ezlevup.ganbyeong24.presentation.screens.admin.care_request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.data.model.CareRequest
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.compose.koinViewModel

/**
 * 관리자용 간병 신청 목록 화면
 *
 * 모든 간병 신청을 조회하고 상태별로 필터링할 수 있습니다.
 *
 * @param onNavigateBack 뒤로가기 콜백
 * @param viewModel ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCareRequestListScreen(
        onNavigateBack: () -> Unit,
        viewModel: AdminCareRequestListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병 신청 관리") },
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
            // 필터 칩
            FilterChips(
                    selectedFilter = state.selectedFilter,
                    onFilterSelected = { viewModel.setFilter(it) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // 목록 내용
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    state.errorMessage != null -> {
                        ErrorContent(
                                errorMessage = state.errorMessage ?: "알 수 없는 오류",
                                onRetry = { viewModel.loadAllCareRequests() }
                        )
                    }
                    state.filteredCareRequests.isEmpty() -> {
                        EmptyContent(selectedFilter = state.selectedFilter)
                    }
                    else -> {
                        LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.filteredCareRequests) { careRequest ->
                                AdminCareRequestItem(careRequest = careRequest)
                            }
                        }
                    }
                }
            }
        }
    }
}

/** 필터 칩 목록 */
@Composable
fun FilterChips(
        selectedFilter: String,
        onFilterSelected: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    val filters =
            listOf(
                    "all" to "전체",
                    "pending" to "대기",
                    "matched" to "매칭",
                    "completed" to "완료",
                    "cancelled" to "취소"
            )

    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters) { (value, label) ->
            FilterChip(
                    selected = selectedFilter == value,
                    onClick = { onFilterSelected(value) },
                    label = { Text(label) }
            )
        }
    }
}

/** 관리자용 간병 신청 카드 */
@Composable
fun AdminCareRequestItem(careRequest: CareRequest) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // 환자명 + 상태 배지
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = careRequest.patientName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                )
                StatusBadge(status = careRequest.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 환자 정보
            InfoRow(label = "나이", value = "${careRequest.patientAge}세")
            InfoRow(label = "성별", value = careRequest.patientGender)
            InfoRow(label = "보호자", value = careRequest.guardianName)
            InfoRow(label = "연락처", value = careRequest.guardianPhoneNumber)
            InfoRow(label = "지역", value = careRequest.location)
            InfoRow(
                    label = "기간",
                    value = "${careRequest.careStartDate} ~ ${careRequest.careEndDate}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 신청 일시
            Text(
                    text = "신청일: ${formatDate(careRequest.createdAt.seconds * 1000)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** 정보 행 */
@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
                text = "$label: ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(70.dp)
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

/** 상태 배지 */
@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor, displayText) =
            when (status) {
                "pending" ->
                        Triple(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.onSecondaryContainer,
                                "대기 중"
                        )
                "matched" ->
                        Triple(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                "매칭 완료"
                        )
                "completed" ->
                        Triple(
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.colorScheme.onTertiaryContainer,
                                "완료"
                        )
                "cancelled" ->
                        Triple(
                                MaterialTheme.colorScheme.errorContainer,
                                MaterialTheme.colorScheme.onErrorContainer,
                                "취소됨"
                        )
                else ->
                        Triple(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                status
                        )
            }

    Surface(
            color = backgroundColor,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(4.dp)
    ) {
        Text(
                text = displayText,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
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
fun EmptyContent(selectedFilter: String) {
    val message =
            when (selectedFilter) {
                "all" -> "간병 신청 내역이 없습니다"
                "pending" -> "대기 중인 신청이 없습니다"
                "matched" -> "매칭된 신청이 없습니다"
                "completed" -> "완료된 신청이 없습니다"
                "cancelled" -> "취소된 신청이 없습니다"
                else -> "신청 내역이 없습니다"
            }

    Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                text = "해당 상태의 간병 신청이 없습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** 날짜 포맷팅 */
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.KOREAN)
    return sdf.format(Date(timestamp))
}
