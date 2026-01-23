package com.ezlevup.ganbyeong24.presentation.screens.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 역할 선택 화면 ViewModel
 *
 * 현재 사용자의 관리자 권한을 확인하고 상태를 관리합니다.
 *
 * @property authRepository 인증 Repository
 * @property userRepository 사용자 Repository
 */
class RoleSelectionViewModel(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository
) : ViewModel() {

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        checkAdminStatus()
    }

    /** 현재 사용자의 관리자 권한을 확인합니다. */
    private fun checkAdminStatus() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userId = authRepository.getCurrentUserId()
                if (userId != null) {
                    userRepository
                            .isAdmin(userId)
                            .onSuccess { isAdmin -> _isAdmin.value = isAdmin }
                            .onFailure { _isAdmin.value = false }
                } else {
                    _isAdmin.value = false
                }
            } catch (e: Exception) {
                _isAdmin.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
