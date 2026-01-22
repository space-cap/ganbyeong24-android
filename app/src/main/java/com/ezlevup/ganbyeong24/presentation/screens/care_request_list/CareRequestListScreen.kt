package com.ezlevup.ganbyeong24.presentation.screens.care_request_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareRequestListScreen(
        onNavigateBack: () -> Unit,
        viewModel: CareRequestListViewModel = koinViewModel()
) {
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) { viewModel.loadCareRequests() }

        Scaffold(
                topBar = {
                        TopAppBar(
                                title = { Text("내 간병 신청 목록") },
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
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        when {
                                state.isLoading -> {
                                        CircularProgressIndicator(
                                                modifier = Modifier.align(Alignment.Center)
                                        )
                                }
                                state.errorMessage != null -> {
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
                                                Text(
                                                        text = state.errorMessage ?: "알 수 없는 오류",
                                                        style = MaterialTheme.typography.bodyMedium
                                                )
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Button(onClick = { viewModel.loadCareRequests() }) {
                                                        Text("다시 시도")
                                                }
                                        }
                                }
                                state.careRequests.isEmpty() -> {
                                        Column(
                                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                        ) {
                                                Text(
                                                        text = "신청 내역이 없습니다",
                                                        style = MaterialTheme.typography.titleLarge
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                        text = "아직 간병 신청을 하지 않으셨습니다.",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color =
                                                                MaterialTheme.colorScheme
                                                                        .onSurfaceVariant
                                                )
                                        }
                                }
                                else -> {
                                        LazyColumn(
                                                modifier = Modifier.fillMaxSize(),
                                                contentPadding = PaddingValues(16.dp),
                                                verticalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                                items(state.careRequests) { careRequest ->
                                                        CareRequestItem(careRequest = careRequest)
                                                }
                                        }
                                }
                        }
                }
        }
}

@Composable
fun CareRequestItem(careRequest: CareRequest) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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

                        InfoRow(label = "나이", value = "${careRequest.patientAge}세")
                        InfoRow(label = "성별", value = careRequest.patientGender)
                        InfoRow(label = "지역", value = careRequest.location)
                        InfoRow(
                                label = "기간",
                                value = "${careRequest.careStartDate} ~ ${careRequest.careEndDate}"
                        )

                        //            if (careRequest.specialRequirements.isNotEmpty()) {
                        //                Spacer(modifier = Modifier.height(4.dp))
                        //                Text(
                        //                        text = "특이사항: ${careRequest.specialRequirements}",
                        //                        style = MaterialTheme.typography.bodySmall,
                        //                        color = MaterialTheme.colorScheme.onSurfaceVariant
                        //                )
                        //            }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                                text = formatDate(careRequest.createdAt.seconds * 1000),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                }
        }
}

@Composable
fun InfoRow(label: String, value: String) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                Text(
                        text = "$label: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.width(60.dp)
                )
                Text(text = value, style = MaterialTheme.typography.bodyMedium)
        }
}

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

fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.KOREAN)
        return sdf.format(Date(timestamp))
}
