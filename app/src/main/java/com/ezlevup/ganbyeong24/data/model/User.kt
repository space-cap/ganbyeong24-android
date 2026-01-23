package com.ezlevup.ganbyeong24.data.model

import com.google.firebase.Timestamp

/**
 * 사용자 정보 데이터 모델
 *
 * Firestore의 users 컬렉션에 저장되는 사용자 정보입니다. Soft Delete 방식을 사용하여 회원 탈퇴 시 실제로 삭제하지 않고 isDeleted 플래그를
 * true로 설정합니다.
 *
 * @property userId Firebase Authentication UID
 * @property email 사용자 이메일
 * @property role 사용자 역할 ("user" 또는 "admin", 기본값: "user")
 * @property createdAt 계정 생성 일시
 * @property isDeleted 삭제 여부 (Soft Delete)
 * @property deletedAt 삭제 일시 (탈퇴한 경우에만 값이 있음)
 */
data class User(
        val userId: String = "",
        val email: String = "",
        val role: String = "user",
        val createdAt: Timestamp = Timestamp.now(),
        val isDeleted: Boolean = false,
        val deletedAt: Timestamp? = null
)
