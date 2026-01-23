package com.ezlevup.ganbyeong24.presentation.screens.admin.match

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.data.model.CareRequest
import com.ezlevup.ganbyeong24.data.model.Caregiver
import com.ezlevup.ganbyeong24.util.SerialNumberFormatter
import org.koin.androidx.compose.koinViewModel

/**
 * 매칭 관리 화면
 *
 * 간병 신청과 간병사를 매칭하는 3단계 프로세스 화면입니다.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchManagementScreen(
        onNavigateBack: () -> Unit,
        viewModel: MatchManagementViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    // 매칭 생성 완료 시 자동으로 뒤로가기
    LaunchedEffect(state.isMatchCreated) {
        if (state.isMatchCreated) {
            kotlinx.coroutines.delay(2000)
            onNavigateBack()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("매칭 관리") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.Default.ArrowBack, "뒤로가기")
                            }
                        }
                )
            }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.isMatchCreated -> {
                    SuccessScreen(state.createdMatchSerialNumber)
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // 단계 표시기
                        StepIndicator(currentStep = state.currentStep)

                        // 단계별 내용
                        Box(modifier = Modifier.weight(1f)) {
                            when (state.currentStep) {
                                1 ->
                                        Step1SelectRequest(
                                                requests = state.careRequests,
                                                selectedRequest = state.selectedRequest,
                                                onSelectRequest = viewModel::selectRequest
                                        )
                                2 ->
                                        Step2SelectCaregiver(
                                                caregivers = state.filteredCaregivers,
                                                selectedCaregiver = state.selectedCaregiver,
                                                selectedRequestLocation =
                                                        state.selectedRequest?.location ?: "",
                                                onSelectCaregiver = viewModel::selectCaregiver
                                        )
                                3 ->
                                        Step3Confirm(
                                                selectedRequest = state.selectedRequest,
                                                selectedCaregiver = state.selectedCaregiver,
                                                notes = state.notes,
                                                onNotesChange = viewModel::setNotes
                                        )
                            }
                        }

                        // 하단 버튼
                        BottomButtons(
                                currentStep = state.currentStep,
                                onPrevious = viewModel::goToPreviousStep,
                                onNext = viewModel::goToNextStep,
                                onCreate = viewModel::createMatch
                        )
                    }
                }
            }

            // 에러 메시지
            state.errorMessage?.let { error ->
                Snackbar(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                        action = { TextButton(onClick = viewModel::clearError) { Text("확인") } }
                ) { Text(error) }
            }
        }
    }
}

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(3) { index ->
            val step = index + 1
            val isActive = step == currentStep
            val isCompleted = step < currentStep

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                        shape = MaterialTheme.shapes.small,
                        color =
                                when {
                                    isCompleted -> MaterialTheme.colorScheme.primary
                                    isActive -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                },
                        modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isCompleted) {
                            Icon(
                                    Icons.Default.Check,
                                    null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                    "$step",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight =
                                            if (isActive) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        when (step) {
                            1 -> "신청 선택"
                            2 -> "간병사 선택"
                            else -> "확인"
                        },
                        style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun Step1SelectRequest(
        requests: List<CareRequest>,
        selectedRequest: CareRequest?,
        onSelectRequest: (CareRequest) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("간병 신청 선택", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        if (requests.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("대기 중인 신청이 없습니다")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(requests) { request ->
                    RequestCard(
                            request = request,
                            isSelected = request.id == selectedRequest?.id,
                            onClick = { onSelectRequest(request) }
                    )
                }
            }
        }
    }
}

@Composable
fun RequestCard(request: CareRequest, isSelected: Boolean, onClick: () -> Unit) {
    Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors =
                    CardDefaults.cardColors(
                            containerColor =
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surface
                    )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                    SerialNumberFormatter.formatCareRequest(request.serialNumber),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    "환자: ${request.patientName} (${request.patientAge}세, ${request.patientGender})",
                    style = MaterialTheme.typography.titleMedium
            )
            Text("지역: ${request.location}", style = MaterialTheme.typography.bodyMedium)
            Text(
                    "기간: ${request.careStartDate} ~ ${request.careEndDate}",
                    style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun Step2SelectCaregiver(
        caregivers: List<Caregiver>,
        selectedCaregiver: Caregiver?,
        selectedRequestLocation: String,
        onSelectCaregiver: (Caregiver) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("간병사 선택", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(caregivers) { caregiver ->
                val isMatching = caregiver.availableRegions.contains(selectedRequestLocation)
                CaregiverCard(
                        caregiver = caregiver,
                        isSelected = caregiver.id == selectedCaregiver?.id,
                        isRegionMatch = isMatching,
                        onClick = { onSelectCaregiver(caregiver) }
                )
            }
        }
    }
}

@Composable
fun CaregiverCard(
        caregiver: Caregiver,
        isSelected: Boolean,
        isRegionMatch: Boolean,
        onClick: () -> Unit
) {
    Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors =
                    CardDefaults.cardColors(
                            containerColor =
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surface
                    )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                        SerialNumberFormatter.formatCaregiver(caregiver.serialNumber),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                )
                if (isRegionMatch) {
                    Spacer(modifier = Modifier.width(8.dp))
                    SuggestionChip(
                            onClick = {},
                            label = { Text("지역 일치", style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    "${caregiver.name} (${caregiver.gender})",
                    style = MaterialTheme.typography.titleMedium
            )
            Text("경력: ${caregiver.experience}", style = MaterialTheme.typography.bodyMedium)
            Text(
                    "자격증: ${caregiver.certificates.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun Step3Confirm(
        selectedRequest: CareRequest?,
        selectedCaregiver: Caregiver?,
        notes: String,
        onNotesChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("매칭 확인", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        selectedRequest?.let {
            Text(
                    "선택된 신청",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
            )
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(SerialNumberFormatter.formatCareRequest(it.serialNumber))
                    Text("환자: ${it.patientName}")
                    Text("지역: ${it.location}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedCaregiver?.let {
            Text(
                    "선택된 간병사",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
            )
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(SerialNumberFormatter.formatCaregiver(it.serialNumber))
                    Text("이름: ${it.name}")
                    Text("경력: ${it.experience}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
                value = notes,
                onValueChange = onNotesChange,
                label = { Text("관리자 메모 (선택)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
        )
    }
}

@Composable
fun BottomButtons(
        currentStep: Int,
        onPrevious: () -> Unit,
        onNext: () -> Unit,
        onCreate: () -> Unit
) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentStep > 1) {
            OutlinedButton(onClick = onPrevious) { Text("이전") }
        } else {
            Spacer(modifier = Modifier.width(1.dp))
        }

        if (currentStep < 3) {
            Button(onClick = onNext) { Text("다음") }
        } else {
            Button(onClick = onCreate) { Text("매칭 생성") }
        }
    }
}

@Composable
fun SuccessScreen(matchSerialNumber: Long) {
    Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text("✅", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("매칭 생성 완료!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                SerialNumberFormatter.formatMatch(matchSerialNumber),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable private fun rememberScrollState() = androidx.compose.foundation.rememberScrollState()

@Composable
private fun Modifier.verticalScroll(state: androidx.compose.foundation.ScrollState) =
        this.then(androidx.compose.foundation.verticalScroll(state))
