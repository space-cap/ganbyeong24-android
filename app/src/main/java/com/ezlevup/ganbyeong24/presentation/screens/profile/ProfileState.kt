package com.ezlevup.ganbyeong24.presentation.screens.profile

import com.google.firebase.Timestamp

/**
 * 프로필 화면 상태
 *
 * @property email 사용자 이메일
 * @property createdAt 계정 생성 일시
 * @property isLoading 로딩 상태
 * @property error 에러 메시지
 * @property isLogoutSuccess 로그아웃 성공 여부
 * @property isDeleteSuccess 회원 탈퇴 성공 여부
 */
data class ProfileState(
        val email: String = "",
        val createdAt: Timestamp? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isLogoutSuccess: Boolean = false,
        val isDeleteSuccess: Boolean = false
)
