package com.ezlevup.ganbyeong24.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 프로필 화면 ViewModel
 *
 * 사용자 정보 조회, 로그아웃, 회원 탈퇴 기능을 제공합니다.
 *
 * @property authRepository 인증 Repository
 * @property userRepository 사용자 Repository
 */
class ProfileViewModel(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUserInfo()
    }

    /** 사용자 정보를 로드합니다. */
    fun loadUserInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                _state.value = _state.value.copy(isLoading = false, error = "로그인 정보를 찾을 수 없습니다")
                return@launch
            }

            userRepository
                    .getUser(userId)
                    .fold(
                            onSuccess = { user ->
                                _state.value =
                                        _state.value.copy(
                                                email = user.email,
                                                createdAt = user.createdAt,
                                                isLoading = false,
                                                error = null
                                        )
                            },
                            onFailure = { exception ->
                                _state.value =
                                        _state.value.copy(
                                                isLoading = false,
                                                error = exception.message ?: "사용자 정보를 불러올 수 없습니다"
                                        )
                            }
                    )
        }
    }

    /** 로그아웃을 처리합니다. */
    fun logout() {
        authRepository.logout()
        _state.value = _state.value.copy(isLogoutSuccess = true)
    }

    /**
     * 회원 탈퇴를 처리합니다.
     * 1. Firestore에서 Soft Delete (isDeleted = true)
     * 2. Firebase Auth 계정 삭제
     */
    fun deleteAccount() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                _state.value = _state.value.copy(isLoading = false, error = "로그인 정보를 찾을 수 없습니다")
                return@launch
            }

            // 1. Firestore에서 Soft Delete
            userRepository
                    .softDeleteUser(userId)
                    .fold(
                            onSuccess = {
                                // 2. Firebase Auth 계정 삭제
                                viewModelScope.launch {
                                    authRepository
                                            .deleteAccount()
                                            .fold(
                                                    onSuccess = {
                                                        _state.value =
                                                                _state.value.copy(
                                                                        isLoading = false,
                                                                        isDeleteSuccess = true
                                                                )
                                                    },
                                                    onFailure = { exception ->
                                                        _state.value =
                                                                _state.value.copy(
                                                                        isLoading = false,
                                                                        error = exception.message
                                                                                        ?: "계정 삭제에 실패했습니다"
                                                                )
                                                    }
                                            )
                                }
                            },
                            onFailure = { exception ->
                                _state.value =
                                        _state.value.copy(
                                                isLoading = false,
                                                error = exception.message ?: "회원 탈퇴에 실패했습니다"
                                        )
                            }
                    )
        }
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
