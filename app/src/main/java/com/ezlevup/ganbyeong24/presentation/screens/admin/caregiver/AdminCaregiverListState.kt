package com.ezlevup.ganbyeong24.presentation.screens.admin.caregiver

import com.ezlevup.ganbyeong24.data.model.Caregiver

/**
 * 관리자용 간병사 목록 화면 상태
 *
 * @property isLoading 로딩 상태
 * @property caregivers 간병사 목록
 * @property filteredCaregivers 필터링된 간병사 목록
 * @property selectedRegion 선택된 지역 필터
 * @property selectedCertificate 선택된 자격증 필터
 * @property selectedExperience 선택된 경력 필터
 * @property errorMessage 에러 메시지
 */
data class AdminCaregiverListState(
        val isLoading: Boolean = false,
        val caregivers: List<Caregiver> = emptyList(),
        val filteredCaregivers: List<Caregiver> = emptyList(),
        val selectedRegion: String? = null,
        val selectedCertificate: String? = null,
        val selectedExperience: String? = null,
        val errorMessage: String? = null
)
