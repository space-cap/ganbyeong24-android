package com.ezlevup.ganbyeong24.presentation.screens.auth

/**
 * 회원가입 화면 상태
 *
 * @property email 이메일
 * @property password 비밀번호
 * @property confirmPassword 비밀번호 확인
 * @property isLoading 로딩 상태
 * @property errorMessage 에러 메시지
 * @property isSuccess 회원가입 성공 여부
 * @property emailError 이메일 에러 메시지
 * @property passwordError 비밀번호 에러 메시지
 * @property confirmPasswordError 비밀번호 확인 에러 메시지
 */
data class SignupState(
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isSuccess: Boolean = false,
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null
)
