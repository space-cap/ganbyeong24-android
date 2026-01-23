package com.ezlevup.ganbyeong24.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

/**
 * 매칭 데이터 모델
 *
 * 간병 신청과 간병사를 연결하는 매칭 정보를 나타냅니다.
 *
 * @property id Firestore 문서 ID
 * @property serialNumber 매칭 일련번호 (사람이 읽기 쉬운 번호)
 * @property careRequestSerialNumber 간병 신청 일련번호
 * @property caregiverSerialNumber 간병사 일련번호
 * @property status 매칭 상태 (pending, confirmed, completed, cancelled)
 * @property notes 관리자 메모
 * @property createdAt 생성 일시
 * @property updatedAt 수정 일시
 */
data class Match(
        @get:Exclude val id: String = "",
        val serialNumber: Long = 0,
        val careRequestSerialNumber: Long = 0,
        val caregiverSerialNumber: Long = 0,
        val status: String = "pending",
        val notes: String = "",
        val createdAt: Timestamp = Timestamp.now(),
        val updatedAt: Timestamp = Timestamp.now()
)
