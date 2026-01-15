package com.ezlevup.ganbyeong24.presentation.screens.care_request

/**
 * 간병 신청 화면 상태
 *
 * 화면의 모든 상태를 관리하는 데이터 클래스입니다.
 *
 * @property isLoading 로딩 상태
 * @property isSuccess 신청 성공 여부
 * @property errorMessage 에러 메시지
 * @property patientName 환자명
 * @property guardianName 보호자명
 * @property patientCondition 환자 상태
 * @property careStartDate 간병 시작일
 * @property careEndDate 간병 종료일
 * @property location 위치
 * @property patientPhoneNumber 환자 연락처
 * @property guardianPhoneNumber 보호자 연락처
 * @property patientNameError 환자명 에러 메시지
 * @property guardianNameError 보호자명 에러 메시지
 * @property patientConditionError 환자 상태 에러 메시지
 * @property careStartDateError 간병 시작일 에러 메시지
 * @property careEndDateError 간병 종료일 에러 메시지
 * @property locationError 위치 에러 메시지
 * @property guardianPhoneNumberError 보호자 연락처 에러 메시지
 */
data class CareRequestState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String? = null,
        val patientName: String = "",
        val guardianName: String = "",
        val patientCondition: String = "",
        val careStartDate: String = "",
        val careEndDate: String = "",
        val location: String = "",
        val patientPhoneNumber: String = "",
        val guardianPhoneNumber: String = "",
        val patientNameError: String? = null,
        val guardianNameError: String? = null,
        val patientConditionError: String? = null,
        val careStartDateError: String? = null,
        val careEndDateError: String? = null,
        val locationError: String? = null,
        val guardianPhoneNumberError: String? = null
)
