package com.ezlevup.ganbyeong24.presentation.screens.care_request_list

import com.ezlevup.ganbyeong24.data.model.CareRequest

/**
 * 간병 신청 목록 화면 상태
 *
 * @property isLoading 로딩 상태
 * @property careRequests 간병 신청 목록
 * @property errorMessage 에러 메시지
 */
data class CareRequestListState(
        val isLoading: Boolean = false,
        val careRequests: List<CareRequest> = emptyList(),
        val errorMessage: String? = null
)
