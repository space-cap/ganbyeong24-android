package com.ezlevup.ganbyeong24.presentation.screens.admin.care_request

import com.ezlevup.ganbyeong24.data.model.CareRequest

/**
 * 관리자용 간병 신청 목록 화면 상태
 *
 * @property isLoading 로딩 상태
 * @property careRequests 간병 신청 목록
 * @property filteredCareRequests 필터링된 간병 신청 목록
 * @property selectedFilter 선택된 필터 ("all", "pending", "matched", "completed", "cancelled")
 * @property errorMessage 에러 메시지
 */
data class AdminCareRequestListState(
        val isLoading: Boolean = false,
        val careRequests: List<CareRequest> = emptyList(),
        val filteredCareRequests: List<CareRequest> = emptyList(),
        val selectedFilter: String = "all",
        val errorMessage: String? = null
)
