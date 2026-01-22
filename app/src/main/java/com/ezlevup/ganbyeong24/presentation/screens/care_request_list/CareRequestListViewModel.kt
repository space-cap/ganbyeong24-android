package com.ezlevup.ganbyeong24.presentation.screens.care_request_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 간병 신청 목록 ViewModel
 *
 * 사용자의 간병 신청 목록을 조회하고 상태를 관리합니다.
 *
 * @property careRequestRepository 간병 신청 Repository
 * @property authRepository 인증 Repository
 */
class CareRequestListViewModel(
        private val careRequestRepository: CareRequestRepository,
        private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CareRequestListState())
    val state: StateFlow<CareRequestListState> = _state.asStateFlow()

    init {
        loadCareRequests()
    }

    /** 현재 사용자의 간병 신청 목록을 조회합니다. */
    fun loadCareRequests() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val userId = authRepository.getCurrentUserId()
            if (userId == null) {
                _state.update { it.copy(isLoading = false, errorMessage = "로그인이 필요합니다") }
                return@launch
            }

            careRequestRepository
                    .getCareRequestsByUserId(userId)
                    .onSuccess { requests ->
                        _state.update { it.copy(isLoading = false, careRequests = requests) }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    errorMessage = error.message ?: "목록을 불러오는데 실패했습니다"
                            )
                        }
                    }
        }
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
