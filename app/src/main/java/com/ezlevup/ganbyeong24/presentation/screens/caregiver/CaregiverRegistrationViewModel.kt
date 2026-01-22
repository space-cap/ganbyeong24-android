package com.ezlevup.ganbyeong24.presentation.screens.caregiver

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.model.Caregiver
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepository
import com.ezlevup.ganbyeong24.util.ImageUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 간병사 등록 화면 ViewModel
 *
 * 간병사 등록 화면의 비즈니스 로직을 담당합니다.
 *
 * @property repository 간병사 Repository
 * @property authRepository 인증 Repository
 */
class CaregiverRegistrationViewModel(
        private val repository: CaregiverRepository,
        private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CaregiverRegistrationState())
    val state: StateFlow<CaregiverRegistrationState> = _state.asStateFlow()

    // ========== 입력 핸들러 ==========

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name, nameError = null) }
    }

    fun onGenderChange(gender: String) {
        _state.update { it.copy(gender = gender, genderError = null) }
    }

    fun onExperienceChange(experience: String) {
        _state.update { it.copy(experience = experience, experienceError = null) }
    }

    fun onCertificateToggle(certificate: String) {
        _state.update { currentState ->
            val updatedCertificates =
                    if (currentState.certificates.contains(certificate)) {
                        currentState.certificates - certificate
                    } else {
                        currentState.certificates + certificate
                    }
            currentState.copy(certificates = updatedCertificates, certificatesError = null)
        }
    }

    fun onRegionToggle(region: String) {
        _state.update { currentState ->
            val updatedRegions =
                    if (currentState.availableRegions.contains(region)) {
                        currentState.availableRegions - region
                    } else {
                        currentState.availableRegions + region
                    }
            currentState.copy(availableRegions = updatedRegions, availableRegionsError = null)
        }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        // 숫자만 추출하여 저장
        val digitsOnly = phoneNumber.filter { it.isDigit() }.take(11)
        _state.update { it.copy(phoneNumber = digitsOnly, phoneNumberError = null) }
    }

    /**
     * 사진 선택 핸들러
     *
     * 선택된 사진을 Base64로 인코딩하여 상태에 저장합니다.
     *
     * @param uri 선택된 사진의 Uri
     * @param context Android Context
     */
    fun onPhotoSelected(uri: Uri, context: Context) {
        viewModelScope.launch {
            val base64String = ImageUtils.uriToBase64(context, uri)
            _state.update { it.copy(photoUri = uri, photoBase64 = base64String) }
        }
    }

    // ========== 등록 처리 ==========

    /** 간병사 등록을 제출합니다. */
    fun registerCaregiver() {
        if (!validateForm()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val userId = authRepository.getCurrentUserId() ?: throw Exception("로그인이 필요합니다")

            val caregiver =
                    Caregiver(
                            userId = userId,
                            name = _state.value.name,
                            gender = _state.value.gender,
                            experience = _state.value.experience,
                            certificates = _state.value.certificates,
                            availableRegions = _state.value.availableRegions,
                            phoneNumber = _state.value.phoneNumber,
                            photoBase64 = _state.value.photoBase64,
                            status = "pending"
                    )

            repository
                    .saveCaregiver(caregiver)
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
     * 폼 유효성 검사를 수행합니다.
     *
     * @return 유효성 검사 통과 여부
     */
    private fun validateForm(): Boolean {
        val errors = mutableMapOf<String, String>()

        // 이름 검증
        if (_state.value.name.length < 2) {
            errors["name"] = "이름은 2자 이상 입력해주세요"
        }

        // 성별 검증
        if (_state.value.gender.isBlank()) {
            errors["gender"] = "성별을 선택해주세요"
        }

        // 경력 검증
        if (_state.value.experience.isBlank()) {
            errors["experience"] = "경력을 입력해주세요"
        }

        // 자격증 검증
        if (_state.value.certificates.isEmpty()) {
            errors["certificates"] = "자격증을 선택해주세요"
        }

        // 가능 지역 검증
        if (_state.value.availableRegions.isEmpty()) {
            errors["availableRegions"] = "가능 지역을 선택해주세요"
        }

        // 연락처 검증
        val phoneRegex = "^010\\d{8}$".toRegex()
        val cleanPhone = _state.value.phoneNumber.replace("-", "")
        if (!phoneRegex.matches(cleanPhone)) {
            errors["phoneNumber"] = "올바른 전화번호 형식이 아닙니다 (010-XXXX-XXXX)"
        }

        // 에러 업데이트
        _state.update {
            it.copy(
                    nameError = errors["name"],
                    genderError = errors["gender"],
                    experienceError = errors["experience"],
                    certificatesError = errors["certificates"],
                    availableRegionsError = errors["availableRegions"],
                    phoneNumberError = errors["phoneNumber"]
            )
        }

        return errors.isEmpty()
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
