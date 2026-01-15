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
 * 로그인 화면 ViewModel
 *
 * @property authRepository 인증 Repository
 */
class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // ========== 입력 핸들러 ==========

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, passwordError = null) }
    }

    // ========== 로그인 처리 ==========

    /** 로그인을 시도합니다. */
    fun login() {
        if (!validateForm()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            authRepository
                    .login(_state.value.email, _state.value.password)
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

        _state.update { it.copy(emailError = errors["email"], passwordError = errors["password"]) }

        return errors.isEmpty()
    }

    /** 에러 메시지를 사용자 친화적으로 변환합니다. */
    private fun getErrorMessage(error: Throwable): String {
        return when {
            error.message?.contains("password") == true -> "이메일 또는 비밀번호가 올바르지 않습니다"
            error.message?.contains("network") == true -> "네트워크 연결을 확인해주세요"
            else -> error.message ?: "로그인에 실패했습니다"
        }
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
