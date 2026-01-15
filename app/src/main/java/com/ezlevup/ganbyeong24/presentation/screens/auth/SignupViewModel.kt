package com.ezlevup.ganbyeong24.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 회원가입 화면 ViewModel
 *
 * @property authRepository 인증 Repository
 */
class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state.asStateFlow()

    // ========== 입력 핸들러 ==========

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    // ========== 회원가입 처리 ==========

    /** 회원가입을 시도합니다. */
    fun signup() {
        if (!validateForm()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            authRepository
                    .signup(_state.value.email, _state.value.password)
                    .onSuccess { _state.update { it.copy(isLoading = false, isSuccess = true) } }
                    .onFailure { error ->
                        _state.update {
                            it.copy(isLoading = false, errorMessage = getErrorMessage(error))
                        }
                    }
        }
    }

    // ========== 유효성 검사 ==========

    /** 폼 유효성 검사를 수행합니다. */
    private fun validateForm(): Boolean {
        val errors = mutableMapOf<String, String>()

        // 이메일 검증
        if (_state.value.email.isBlank()) {
            errors["email"] = "이메일을 입력해주세요"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()) {
            errors["email"] = "올바른 이메일 형식이 아닙니다"
        }

        // 비밀번호 검증
        if (_state.value.password.isBlank()) {
            errors["password"] = "비밀번호를 입력해주세요"
        } else if (_state.value.password.length < 6) {
            errors["password"] = "비밀번호는 6자 이상이어야 합니다"
        }

        // 비밀번호 확인 검증
        if (_state.value.confirmPassword.isBlank()) {
            errors["confirmPassword"] = "비밀번호 확인을 입력해주세요"
        } else if (_state.value.password != _state.value.confirmPassword) {
            errors["confirmPassword"] = "비밀번호가 일치하지 않습니다"
        }

        _state.update {
            it.copy(
                    emailError = errors["email"],
                    passwordError = errors["password"],
                    confirmPasswordError = errors["confirmPassword"]
            )
        }

        return errors.isEmpty()
    }

    /** 에러 메시지를 사용자 친화적으로 변환합니다. */
    private fun getErrorMessage(error: Throwable): String {
        return when {
            error.message?.contains("already") == true -> "이미 사용 중인 이메일입니다"
            error.message?.contains("network") == true -> "네트워크 연결을 확인해주세요"
            else -> error.message ?: "회원가입에 실패했습니다"
        }
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
