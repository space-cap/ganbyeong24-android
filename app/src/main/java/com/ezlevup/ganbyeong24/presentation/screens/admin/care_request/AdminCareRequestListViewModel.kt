package com.ezlevup.ganbyeong24.presentation.screens.admin.care_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 관리자용 간병 신청 목록 ViewModel
 *
 * 모든 간병 신청을 조회하고 상태별 필터링을 제공합니다.
 *
 * @property careRequestRepository 간병 신청 Repository
 */
class AdminCareRequestListViewModel(private val careRequestRepository: CareRequestRepository) :
        ViewModel() {

    private val _state = MutableStateFlow(AdminCareRequestListState())
    val state: StateFlow<AdminCareRequestListState> = _state.asStateFlow()

    init {
        loadAllCareRequests()
    }

    /** 모든 간병 신청 목록을 조회합니다. */
    fun loadAllCareRequests() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            careRequestRepository
                    .getAllCareRequests()
                    .onSuccess { requests ->
                        _state.update {
                            it.copy(
                                    isLoading = false,
                                    careRequests = requests,
                                    filteredCareRequests =
                                            filterRequests(requests, it.selectedFilter)
                            )
                        }
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

    /**
     * 필터를 변경합니다.
     *
     * @param filter 필터 값 ("all", "pending", "matched", "completed", "cancelled")
     */
    fun setFilter(filter: String) {
        _state.update {
            it.copy(
                    selectedFilter = filter,
                    filteredCareRequests = filterRequests(it.careRequests, filter)
            )
        }
    }

    /**
     * 상태별로 간병 신청을 필터링합니다.
     *
     * @param requests 전체 간병 신청 목록
     * @param filter 필터 값
     * @return 필터링된 간병 신청 목록
     */
    private fun filterRequests(
            requests: List<com.ezlevup.ganbyeong24.data.model.CareRequest>,
            filter: String
    ): List<com.ezlevup.ganbyeong24.data.model.CareRequest> {
        return when (filter) {
            "all" -> requests
            else -> requests.filter { it.status == filter }
        }
    }

    /** 에러 메시지를 초기화합니다. */
    fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }
}
