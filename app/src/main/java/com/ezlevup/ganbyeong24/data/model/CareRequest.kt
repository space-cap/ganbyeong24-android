package com.ezlevup.ganbyeong24.data.model

import com.google.firebase.Timestamp

/**
 * 간병 신청 데이터 모델
 *
 * Firestore에 저장되는 간병 신청 정보를 나타냅니다.
 *
 * @property id Firestore 문서 ID
 * @property userId 사용자 ID (Firebase Auth)
 * @property patientName 환자명
 * @property guardianName 보호자명
 * @property patientCondition 환자 상태
 * @property careStartDate 간병 시작일
 * @property careEndDate 간병 종료일
 * @property location 간병 위치
 * @property patientPhoneNumber 환자 연락처 (선택)
 * @property guardianPhoneNumber 보호자 연락처 (필수)
 * @property status 신청 상태 (pending, approved, rejected)
 * @property createdAt 생성 시간
 */
data class CareRequest(
        val id: String = "",
        val userId: String = "",
        val patientName: String = "",
        val guardianName: String = "",
        val patientCondition: String = "",
        val careStartDate: String = "",
        val careEndDate: String = "",
        val location: String = "",
        val patientPhoneNumber: String? = null,
        val guardianPhoneNumber: String = "",
        val status: String = "pending",
        val createdAt: Timestamp = Timestamp.now()
)
