package com.ezlevup.ganbyeong24.presentation.screens.care_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.model.CareRequest
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 간병 신청 화면 ViewModel
 *
 * 간병 신청 화면의 비즈니스 로직을 담당합니다.
 *
 * @property repository 간병 신청 Repository
 * @property authRepository 인증 Repository
 */
class CareRequestViewModel(
        private val repository: CareRequestRepository,
        private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CareRequestState())
    val state: StateFlow<CareRequestState> = _state.asStateFlow()

    // ========== 단계 네비게이션 ==========

    /** 다음 단계로 이동합니다. */
    fun nextStep() {
        if (validateCurrentStep()) {
            _state.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(3)) }
        }
    }

    /** 이전 단계로 이동합니다. */
    fun previousStep() {
        _state.update { it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(1)) }
    }

    // ========== 입력 핸들러 ==========

    fun onPatientNameChange(name: String) {
        _state.update { it.copy(patientName = name, patientNameError = null) }
    }

    fun onGuardianNameChange(name: String) {
        _state.update { it.copy(guardianName = name, guardianNameError = null) }
    }

    fun onPatientConditionChange(condition: String) {
        _state.update { it.copy(patientCondition = condition, patientConditionError = null) }
    }

    fun onCareStartDateChange(date: String) {
        _state.update { it.copy(careStartDate = date, careStartDateError = null) }
    }

    fun onCareEndDateChange(date: String) {
        _state.update { it.copy(careEndDate = date, careEndDateError = null) }
    }

    fun onCityChange(city: String) {
        _state.update {
            it.copy(
                    city = city,
                    location =
                            if (city.isNotEmpty() && it.district.isNotEmpty()) {
                                "$city ${it.district}"
                            } else {
                                ""
                            },
                    locationError = null
            )
        }
    }

    fun onDistrictChange(district: String) {
        _state.update {
            it.copy(
                    district = district,
                    location =
                            if (it.city.isNotEmpty() && district.isNotEmpty()) {
                                "${it.city} $district"
                            } else {
                                ""
                            },
                    locationError = null
            )
        }
    }

    fun onLocationChange(location: String) {
        _state.update { it.copy(location = location, locationError = null) }
    }

    fun onPatientPhoneNumberChange(phoneNumber: String) {
        // 숫자만 추출하여 저장 (VisualTransformation이 포맷팅 담당)
        val digitsOnly = phoneNumber.filter { it.isDigit() }.take(11)
        _state.update { it.copy(patientPhoneNumber = digitsOnly) }
    }

    fun onGuardianPhoneNumberChange(phoneNumber: String) {
        // 숫자만 추출하여 저장 (VisualTransformation이 포맷팅 담당)
        val digitsOnly = phoneNumber.filter { it.isDigit() }.take(11)
        _state.update { it.copy(guardianPhoneNumber = digitsOnly, guardianPhoneNumberError = null) }
    }

    // ========== 신청 처리 ==========

    /** 간병 신청을 제출합니다. */
    fun submitCareRequest() {
        if (!validateForm()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val userId = authRepository.getCurrentUserId() ?: throw Exception("로그인이 필요합니다")

            val request =
                    CareRequest(
                            userId = userId,
                            patientName = _state.value.patientName,
                            guardianName = _state.value.guardianName,
                            patientCondition = _state.value.patientCondition,
                            careStartDate = _state.value.careStartDate,
                            careEndDate = _state.value.careEndDate,
                            location = _state.value.location,
                            patientPhoneNumber = _state.value.patientPhoneNumber.ifBlank { null },
                            guardianPhoneNumber = _state.value.guardianPhoneNumber,
                            status = "pending"
                    )

            repository
                    .saveCareRequest(request)
                    .onSuccess { _state.update { it.copy(isLoading = false, isSuccess = true) } }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    errorMessage = error.message ?: "알 수 없는 오류가 발생했습니다"
                            )
                        }
                    }
        }
    }

    // ========== 유효성 검사 ==========

    /**
     * 현재 단계의 유효성 검사를 수행합니다.
     *
     * @return 유효성 검사 통과 여부
     */
    private fun validateCurrentStep(): Boolean {
        return when (_state.value.currentStep) {
            1 -> validateStep1()
            2 -> validateStep2()
            3 -> validateStep3()
            else -> false
        }
    }

    /** Step 1: 환자 정보 검증 */
    private fun validateStep1(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (_state.value.patientName.length < 2) {
            errors["patientName"] = "환자명은 2자 이상 입력해주세요"
        }

        if (_state.value.patientCondition.isBlank()) {
            errors["patientCondition"] = "환자 상태를 선택해주세요"
        }

        _state.update {
            it.copy(
                    patientNameError = errors["patientName"],
                    patientConditionError = errors["patientCondition"]
            )
        }

        return errors.isEmpty()
    }

    /** Step 2: 간병 기간 검증 */
    private fun validateStep2(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (_state.value.careStartDate.isBlank()) {
            errors["careStartDate"] = "간병 시작일을 선택해주세요"
        }

        if (_state.value.careEndDate.isBlank()) {
            errors["careEndDate"] = "간병 종료일을 선택해주세요"
        }

        _state.update {
            it.copy(
                    careStartDateError = errors["careStartDate"],
                    careEndDateError = errors["careEndDate"]
            )
        }

        return errors.isEmpty()
    }

    /** Step 3: 연락처 및 위치 검증 */
    private fun validateStep3(): Boolean {
        val errors = mutableMapOf<String, String>()

        if (_state.value.guardianName.length < 2) {
            errors["guardianName"] = "보호자명은 2자 이상 입력해주세요"
        }

        if (_state.value.location.isBlank()) {
            errors["location"] = "위치를 입력해주세요"
        }

        val phoneRegex = "^010\\d{8}$".toRegex()
        val cleanPhone = _state.value.guardianPhoneNumber.replace("-", "")
        if (!phoneRegex.matches(cleanPhone)) {
            errors["guardianPhoneNumber"] = "올바른 전화번호 형식이 아닙니다 (010-XXXX-XXXX)"
        }

        _state.update {
            it.copy(
                    guardianNameError = errors["guardianName"],
                    locationError = errors["location"],
                    guardianPhoneNumberError = errors["guardianPhoneNumber"]
            )
        }

        return errors.isEmpty()
    }

    /**
     * 폼 유효성 검사를 수행합니다.
     *
     * @return 유효성 검사 통과 여부
     */
    private fun validateForm(): Boolean {
        val errors = mutableMapOf<String, String>()

        // 환자명 검증
        if (_state.value.patientName.length < 2) {
            errors["patientName"] = "환자명은 2자 이상 입력해주세요"
        }

        // 보호자명 검증
        if (_state.value.guardianName.length < 2) {
            errors["guardianName"] = "보호자명은 2자 이상 입력해주세요"
        }

        // 환자 상태 검증
        if (_state.value.patientCondition.isBlank()) {
            errors["patientCondition"] = "환자 상태를 입력해주세요"
        }

        // 간병 시작일 검증
        if (_state.value.careStartDate.isBlank()) {
            errors["careStartDate"] = "간병 시작일을 입력해주세요"
        }

        // 간병 종료일 검증
        if (_state.value.careEndDate.isBlank()) {
            errors["careEndDate"] = "간병 종료일을 입력해주세요"
        }

        // 위치 검증
        if (_state.value.location.isBlank()) {
            errors["location"] = "위치를 입력해주세요"
        }

        // 보호자 연락처 검증
        val phoneRegex = "^010\\d{8}$".toRegex()
        val cleanPhone = _state.value.guardianPhoneNumber.replace("-", "")
        if (!phoneRegex.matches(cleanPhone)) {
            errors["guardianPhoneNumber"] = "올바른 전화번호 형식이 아닙니다 (010-XXXX-XXXX)"
        }

        // 에러 업데이트
        _state.update {
            it.copy(
                    patientNameError = errors["patientName"],
                    guardianNameError = errors["guardianName"],
                    patientConditionError = errors["patientCondition"],
                    careStartDateError = errors["careStartDate"],
                    careEndDateError = errors["careEndDate"],
                    locationError = errors["location"],
                    guardianPhoneNumberError = errors["guardianPhoneNumber"]
            )
        }

        return errors.isEmpty()
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
