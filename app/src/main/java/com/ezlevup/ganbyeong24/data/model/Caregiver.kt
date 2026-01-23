package com.ezlevup.ganbyeong24.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

/**
 * 간병사 데이터 모델
 *
 * Firestore에 저장되는 간병사 정보를 나타냅니다.
 *
 * @property id Firestore 문서 ID
 * @property serialNumber 간병사 일련번호 (사람이 읽기 쉬운 번호)
 * @property userId 사용자 ID (Firebase Auth)
 * @property name 이름
 * @property gender 성별 (남성, 여성)
 * @property experience 경력
 * @property certificates 자격증 목록
 * @property availableRegions 가능 지역 목록
 * @property phoneNumber 연락처
 * @property photoBase64 프로필 사진 Base64 인코딩 문자열
 * @property status 등록 상태 (pending, approved, rejected)
 * @property createdAt 생성 시간
 */
data class Caregiver(
        @get:Exclude val id: String = "",
        val serialNumber: Int = 0,
        val userId: String = "",
        val name: String = "",
        val gender: String = "",
        val experience: String = "",
        val certificates: List<String> = emptyList(),
        val availableRegions: List<String> = emptyList(),
        val phoneNumber: String = "",
        val photoBase64: String? = null,
        val status: String = "pending",
        val createdAt: Timestamp = Timestamp.now()
)
