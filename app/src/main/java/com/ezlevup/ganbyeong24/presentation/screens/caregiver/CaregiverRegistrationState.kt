package com.ezlevup.ganbyeong24.presentation.screens.caregiver

/**
 * 간병사 등록 화면 상태
 *
 * 화면의 모든 상태를 관리하는 데이터 클래스입니다.
 *
 * @property isLoading 로딩 상태
 * @property isSuccess 등록 성공 여부
 * @property errorMessage 에러 메시지
 * @property name 이름
 * @property experience 경력
 * @property certificates 자격증
 * @property availableRegions 가능 지역
 * @property phoneNumber 연락처
 * @property nameError 이름 에러 메시지
 * @property experienceError 경력 에러 메시지
 * @property certificatesError 자격증 에러 메시지
 * @property availableRegionsError 가능 지역 에러 메시지
 * @property phoneNumberError 연락처 에러 메시지
 */
data class CaregiverRegistrationState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String? = null,
        val name: String = "",
        val experience: String = "",
        val certificates: String = "",
        val availableRegions: String = "",
        val phoneNumber: String = "",
        val nameError: String? = null,
        val experienceError: String? = null,
        val certificatesError: String? = null,
        val availableRegionsError: String? = null,
        val phoneNumberError: String? = null
)
