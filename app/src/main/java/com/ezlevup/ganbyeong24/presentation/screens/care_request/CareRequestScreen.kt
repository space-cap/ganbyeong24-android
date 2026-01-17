package com.ezlevup.ganbyeong24.presentation.screens.care_request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.components.DatePickerField
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongTextField
import com.ezlevup.ganbyeong24.presentation.components.LocationSelector
import com.ezlevup.ganbyeong24.presentation.components.PatientConditionSelector
import com.ezlevup.ganbyeong24.presentation.components.RecentPatientChips
import com.ezlevup.ganbyeong24.presentation.components.StepIndicator
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import com.ezlevup.ganbyeong24.util.PhoneNumberVisualTransformation
import org.koin.androidx.compose.koinViewModel

/**
 * 간병 신청 화면
 *
 * 보호자가 간병 서비스를 신청할 수 있는 화면입니다. 3단계로 나누어 시니어 사용자가 쉽게 입력할 수 있도록 구성되었습니다.
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
        val recentPatients by viewModel.recentPatients.collectAsState()

        // 신청 성공 시 ResultScreen으로 이동
        LaunchedEffect(state.isSuccess) {
                if (state.isSuccess) {
                        onSuccess()
                }
        }

        Scaffold(
                topBar = {
                        TopAppBar(
                                title = { Text("간병 신청", fontSize = 22.sp) },
                                navigationIcon = {
                                        IconButton(onClick = onNavigateBack) {
                                                Icon(
                                                        Icons.Default.ArrowBack,
                                                        contentDescription = "뒤로가기",
                                                        modifier = Modifier.size(28.dp)
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
                                        .padding(horizontal = 32.dp, vertical = 24.dp)
                ) {
                        // 단계 인디케이터
                        StepIndicator(
                                currentStep = state.currentStep,
                                totalSteps = 3,
                                stepLabels = listOf("환자 정보", "간병 기간", "연락처"),
                                modifier = Modifier.padding(bottom = 32.dp)
                        )

                        // 현재 단계에 따른 컨텐츠
                        when (state.currentStep) {
                                1 ->
                                        Step1Content(
                                                state = state,
                                                recentPatients = recentPatients,
                                                onPatientNameChange =
                                                        viewModel::onPatientNameChange,
                                                onPatientConditionChange =
                                                        viewModel::onPatientConditionChange,
                                                onRecentPatientRemoved =
                                                        viewModel::removeRecentPatient
                                        )
                                2 ->
                                        Step2Content(
                                                state = state,
                                                onCareStartDateChange =
                                                        viewModel::onCareStartDateChange,
                                                onCareEndDateChange = viewModel::onCareEndDateChange
                                        )
                                3 ->
                                        Step3Content(
                                                state = state,
                                                onGuardianNameChange =
                                                        viewModel::onGuardianNameChange,
                                                onGuardianPhoneNumberChange =
                                                        viewModel::onGuardianPhoneNumberChange,
                                                onCityChange = viewModel::onCityChange,
                                                onDistrictChange = viewModel::onDistrictChange,
                                                onPatientPhoneNumberChange =
                                                        viewModel::onPatientPhoneNumberChange
                                        )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // 네비게이션 버튼
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                                // 이전 버튼
                                if (state.currentStep > 1) {
                                        OutlinedButton(
                                                onClick = viewModel::previousStep,
                                                modifier = Modifier.weight(1f).height(56.dp),
                                                shape = RoundedCornerShape(12.dp)
                                        ) {
                                                Text(
                                                        "이전",
                                                        fontSize = 20.sp,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }
                                }

                                // 다음/신청 버튼
                                GanbyeongButton(
                                        text = if (state.currentStep == 3) "신청하기" else "다음",
                                        onClick = {
                                                if (state.currentStep == 3) {
                                                        viewModel.submitCareRequest()
                                                } else {
                                                        viewModel.nextStep()
                                                }
                                        },
                                        isLoading = state.isLoading,
                                        modifier =
                                                Modifier.weight(
                                                                if (state.currentStep > 1) 1f
                                                                else 1f
                                                        )
                                                        .height(56.dp)
                                )
                        }
                }
        }

        // 에러 다이얼로그
        if (state.errorMessage != null) {
                AlertDialog(
                        onDismissRequest = viewModel::clearError,
                        title = { Text("오류", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                        text = { Text(state.errorMessage!!, fontSize = 18.sp) },
                        confirmButton = {
                                TextButton(onClick = viewModel::clearError) {
                                        Text("확인", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                        }
                )
        }
}

// ========== Step 1: 환자 정보 ==========

@Composable
private fun Step1Content(
        state: CareRequestState,
        recentPatients: List<String>,
        onPatientNameChange: (String) -> Unit,
        onPatientConditionChange: (String) -> Unit,
        onRecentPatientRemoved: (String) -> Unit
) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                        text = "환자 정보를 입력해주세요",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                )

                // 최근 신청 환자 칩
                RecentPatientChips(
                        recentPatients = recentPatients,
                        onPatientSelected = onPatientNameChange,
                        onPatientRemoved = onRecentPatientRemoved
                )

                // 환자명
                GanbyeongTextField(
                        value = state.patientName,
                        onValueChange = onPatientNameChange,
                        label = "환자명 *",
                        placeholder = "예: 홍길동",
                        isError = state.patientNameError != null,
                        errorMessage = state.patientNameError,
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
                )

                // 환자 상태 선택
                PatientConditionSelector(
                        selectedCondition = state.patientCondition,
                        onConditionSelected = onPatientConditionChange
                )

                if (state.patientConditionError != null) {
                        Text(
                                text = state.patientConditionError,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 16.sp
                        )
                }
        }
}

// ========== Step 2: 간병 기간 ==========

@Composable
private fun Step2Content(
        state: CareRequestState,
        onCareStartDateChange: (String) -> Unit,
        onCareEndDateChange: (String) -> Unit
) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                        text = "간병 기간을 선택해주세요",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                )

                // 간병 시작일
                DatePickerField(
                        label = "간병 시작일 *",
                        selectedDate = state.careStartDate,
                        onDateSelected = onCareStartDateChange,
                        isError = state.careStartDateError != null,
                        errorMessage = state.careStartDateError
                )

                // 간병 종료일
                DatePickerField(
                        label = "간병 종료일 *",
                        selectedDate = state.careEndDate,
                        onDateSelected = onCareEndDateChange,
                        isError = state.careEndDateError != null,
                        errorMessage = state.careEndDateError
                )
        }
}

// ========== Step 3: 연락처 및 위치 ==========

@Composable
private fun Step3Content(
        state: CareRequestState,
        onGuardianNameChange: (String) -> Unit,
        onGuardianPhoneNumberChange: (String) -> Unit,
        onCityChange: (String) -> Unit,
        onDistrictChange: (String) -> Unit,
        onPatientPhoneNumberChange: (String) -> Unit
) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text(
                        text = "연락처 정보를 입력해주세요",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                )

                // 보호자명
                GanbyeongTextField(
                        value = state.guardianName,
                        onValueChange = onGuardianNameChange,
                        label = "보호자명 *",
                        placeholder = "예: 김철수",
                        isError = state.guardianNameError != null,
                        errorMessage = state.guardianNameError,
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
                )

                // 보호자 연락처
                GanbyeongTextField(
                        value = state.guardianPhoneNumber,
                        onValueChange = onGuardianPhoneNumberChange,
                        label = "보호자 연락처 *",
                        placeholder = "010-1234-5678",
                        isError = state.guardianPhoneNumberError != null,
                        errorMessage = state.guardianPhoneNumberError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        visualTransformation = PhoneNumberVisualTransformation(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
                )

                // 위치 (드롭다운)
                LocationSelector(
                        selectedCity = state.city,
                        selectedDistrict = state.district,
                        onCitySelected = onCityChange,
                        onDistrictSelected = onDistrictChange,
                        isError = state.locationError != null,
                        errorMessage = state.locationError
                )

                // 환자 연락처 (선택)
                GanbyeongTextField(
                        value = state.patientPhoneNumber,
                        onValueChange = onPatientPhoneNumberChange,
                        label = "환자 연락처 (선택)",
                        placeholder = "010-9876-5432",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        visualTransformation = PhoneNumberVisualTransformation(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
                )
        }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun Step1Preview() {
        GanbyeongTheme {
                Column(modifier = Modifier.padding(32.dp)) {
                        Step1Content(
                                state =
                                        CareRequestState(
                                                patientName = "홍길동",
                                                patientCondition = "거동 가능"
                                        ),
                                recentPatients = listOf("김철수", "이영희"),
                                onPatientNameChange = {},
                                onPatientConditionChange = {},
                                onRecentPatientRemoved = {}
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun Step2Preview() {
        GanbyeongTheme {
                Column(modifier = Modifier.padding(32.dp)) {
                        Step2Content(
                                state =
                                        CareRequestState(
                                                careStartDate = "2024-01-15",
                                                careEndDate = "2024-01-30"
                                        ),
                                onCareStartDateChange = {},
                                onCareEndDateChange = {}
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun Step3Preview() {
        GanbyeongTheme {
                Column(modifier = Modifier.padding(32.dp)) {
                        Step3Content(
                                state =
                                        CareRequestState(
                                                guardianName = "김철수",
                                                guardianPhoneNumber = "010-1234-5678",
                                                location = "서울시 강남구"
                                        ),
                                onGuardianNameChange = {},
                                onGuardianPhoneNumberChange = {},
                                onCityChange = {},
                                onDistrictChange = {},
                                onPatientPhoneNumberChange = {}
                        )
                }
        }
}
