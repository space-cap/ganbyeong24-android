package com.ezlevup.ganbyeong24.presentation.screens.care_request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongTextField
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import org.koin.androidx.compose.koinViewModel

/**
 * 간병 신청 화면
 *
 * 보호자가 간병 서비스를 신청할 수 있는 화면입니다.
 *
 * @param viewModel 간병 신청 ViewModel
 * @param onNavigateBack 뒤로가기 콜백
 * @param onSuccess 신청 성공 시 호출되는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareRequestScreen(
        viewModel: CareRequestViewModel = koinViewModel(),
        onNavigateBack: () -> Unit = {},
        onSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // 신청 성공 시 ResultScreen으로 이동
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onSuccess()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병 신청") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
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
            // 환자명
            GanbyeongTextField(
                    value = state.patientName,
                    onValueChange = viewModel::onPatientNameChange,
                    label = "환자명 *",
                    placeholder = "예: 홍길동",
                    isError = state.patientNameError != null,
                    errorMessage = state.patientNameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 보호자명
            GanbyeongTextField(
                    value = state.guardianName,
                    onValueChange = viewModel::onGuardianNameChange,
                    label = "보호자명 *",
                    placeholder = "예: 김철수",
                    isError = state.guardianNameError != null,
                    errorMessage = state.guardianNameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 환자 상태
            GanbyeongTextField(
                    value = state.patientCondition,
                    onValueChange = viewModel::onPatientConditionChange,
                    label = "환자 상태 *",
                    placeholder = "예: 거동 가능",
                    isError = state.patientConditionError != null,
                    errorMessage = state.patientConditionError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 간병 시작일
            GanbyeongTextField(
                    value = state.careStartDate,
                    onValueChange = viewModel::onCareStartDateChange,
                    label = "간병 시작일 *",
                    placeholder = "예: 2024-01-15",
                    isError = state.careStartDateError != null,
                    errorMessage = state.careStartDateError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 간병 종료일
            GanbyeongTextField(
                    value = state.careEndDate,
                    onValueChange = viewModel::onCareEndDateChange,
                    label = "간병 종료일 *",
                    placeholder = "예: 2024-01-30",
                    isError = state.careEndDateError != null,
                    errorMessage = state.careEndDateError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 위치
            GanbyeongTextField(
                    value = state.location,
                    onValueChange = viewModel::onLocationChange,
                    label = "위치 *",
                    placeholder = "예: 서울시 강남구",
                    isError = state.locationError != null,
                    errorMessage = state.locationError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 환자 연락처 (선택)
            GanbyeongTextField(
                    value = state.patientPhoneNumber,
                    onValueChange = viewModel::onPatientPhoneNumberChange,
                    label = "환자 연락처 (선택)",
                    placeholder = "010-1234-5678",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 보호자 연락처 (필수)
            GanbyeongTextField(
                    value = state.guardianPhoneNumber,
                    onValueChange = viewModel::onGuardianPhoneNumberChange,
                    label = "보호자 연락처 *",
                    placeholder = "010-9876-5432",
                    isError = state.guardianPhoneNumberError != null,
                    errorMessage = state.guardianPhoneNumberError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            // 신청 버튼
            GanbyeongButton(
                    text = "신청하기",
                    onClick = viewModel::submitCareRequest,
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
                confirmButton = { TextButton(onClick = viewModel::clearError) { Text("확인") } }
        )
    }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun CareRequestScreenPreview() {
    GanbyeongTheme {
        // Preview는 ViewModel 없이 표시
        CareRequestScreenContent(
                state = CareRequestState(),
                onPatientNameChange = {},
                onGuardianNameChange = {},
                onPatientConditionChange = {},
                onCareStartDateChange = {},
                onCareEndDateChange = {},
                onLocationChange = {},
                onPatientPhoneNumberChange = {},
                onGuardianPhoneNumberChange = {},
                onSubmit = {},
                onNavigateBack = {},
                onClearError = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CareRequestScreenContent(
        state: CareRequestState,
        onPatientNameChange: (String) -> Unit,
        onGuardianNameChange: (String) -> Unit,
        onPatientConditionChange: (String) -> Unit,
        onCareStartDateChange: (String) -> Unit,
        onCareEndDateChange: (String) -> Unit,
        onLocationChange: (String) -> Unit,
        onPatientPhoneNumberChange: (String) -> Unit,
        onGuardianPhoneNumberChange: (String) -> Unit,
        onSubmit: () -> Unit,
        onNavigateBack: () -> Unit,
        onClearError: () -> Unit
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병 신청") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
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
                    value = state.patientName,
                    onValueChange = onPatientNameChange,
                    label = "환자명 *",
                    placeholder = "예: 홍길동",
                    isError = state.patientNameError != null,
                    errorMessage = state.patientNameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.guardianName,
                    onValueChange = onGuardianNameChange,
                    label = "보호자명 *",
                    placeholder = "예: 김철수",
                    isError = state.guardianNameError != null,
                    errorMessage = state.guardianNameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.patientCondition,
                    onValueChange = onPatientConditionChange,
                    label = "환자 상태 *",
                    placeholder = "예: 거동 가능",
                    isError = state.patientConditionError != null,
                    errorMessage = state.patientConditionError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.careStartDate,
                    onValueChange = onCareStartDateChange,
                    label = "간병 시작일 *",
                    placeholder = "예: 2024-01-15",
                    isError = state.careStartDateError != null,
                    errorMessage = state.careStartDateError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.careEndDate,
                    onValueChange = onCareEndDateChange,
                    label = "간병 종료일 *",
                    placeholder = "예: 2024-01-30",
                    isError = state.careEndDateError != null,
                    errorMessage = state.careEndDateError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.location,
                    onValueChange = onLocationChange,
                    label = "위치 *",
                    placeholder = "예: 서울시 강남구",
                    isError = state.locationError != null,
                    errorMessage = state.locationError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.patientPhoneNumber,
                    onValueChange = onPatientPhoneNumberChange,
                    label = "환자 연락처 (선택)",
                    placeholder = "010-1234-5678",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.guardianPhoneNumber,
                    onValueChange = onGuardianPhoneNumberChange,
                    label = "보호자 연락처 *",
                    placeholder = "010-9876-5432",
                    isError = state.guardianPhoneNumberError != null,
                    errorMessage = state.guardianPhoneNumberError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            GanbyeongButton(text = "신청하기", onClick = onSubmit, isLoading = state.isLoading)
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
