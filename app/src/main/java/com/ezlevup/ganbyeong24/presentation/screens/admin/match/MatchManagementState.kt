package com.ezlevup.ganbyeong24.presentation.screens.admin.match

import com.ezlevup.ganbyeong24.data.model.CareRequest
import com.ezlevup.ganbyeong24.data.model.Caregiver

/**
 * 매칭 관리 화면 상태
 *
 * @property isLoading 로딩 상태
 * @property currentStep 현재 단계 (1: 신청 선택, 2: 간병사 선택, 3: 확인)
 * @property careRequests pending 상태 간병 신청 목록
 * @property caregivers 간병사 목록
 * @property filteredCaregivers 필터링된 간병사 목록
 * @property selectedRequest 선택된 간병 신청
 * @property selectedCaregiver 선택된 간병사
 * @property notes 관리자 메모
 * @property errorMessage 에러 메시지
 * @property isMatchCreated 매칭 생성 완료 여부
 * @property createdMatchSerialNumber 생성된 매칭 일련번호
 */
data class MatchManagementState(
        val isLoading: Boolean = false,
        val currentStep: Int = 1,
        val careRequests: List<CareRequest> = emptyList(),
        val caregivers: List<Caregiver> = emptyList(),
        val filteredCaregivers: List<Caregiver> = emptyList(),
        val selectedRequest: CareRequest? = null,
        val selectedCaregiver: Caregiver? = null,
        val notes: String = "",
        val errorMessage: String? = null,
        val isMatchCreated: Boolean = false,
        val createdMatchSerialNumber: Long = 0
)
