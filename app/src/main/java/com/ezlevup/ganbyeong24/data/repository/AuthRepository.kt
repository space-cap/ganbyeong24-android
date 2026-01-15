package com.ezlevup.ganbyeong24.data.repository

/**
 * 인증 Repository 인터페이스
 *
 * 사용자 인증 관련 기능을 담당합니다.
 */
interface AuthRepository {
    /**
     * 이메일과 비밀번호로 로그인합니다.
     *
     * @param email 이메일
     * @param password 비밀번호
     * @return Result<String> 성공 시 사용자 ID, 실패 시 에러
     */
    suspend fun login(email: String, password: String): Result<String>

    /**
     * 이메일과 비밀번호로 회원가입합니다.
     *
     * @param email 이메일
     * @param password 비밀번호
     * @return Result<String> 성공 시 사용자 ID, 실패 시 에러
     */
    suspend fun signup(email: String, password: String): Result<String>

    /** 로그아웃합니다. */
    fun logout()

    /**
     * 현재 로그인한 사용자의 ID를 반환합니다.
     *
     * @return 사용자 ID, 로그인하지 않은 경우 null
     */
    fun getCurrentUserId(): String?

    /**
     * 현재 사용자가 로그인했는지 확인합니다.
     *
     * @return 로그인 여부
     */
    fun isLoggedIn(): Boolean
}
