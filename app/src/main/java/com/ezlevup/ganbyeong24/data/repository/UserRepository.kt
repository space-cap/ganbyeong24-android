package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.User

/**
 * 사용자 정보 Repository 인터페이스
 *
 * Firestore의 users 컬렉션을 관리합니다.
 */
interface UserRepository {
    /**
     * 새로운 사용자를 생성합니다.
     *
     * @param userId Firebase Authentication UID
     * @param email 사용자 이메일
     * @return Result<Unit> 성공 또는 실패
     */
    suspend fun createUser(userId: String, email: String): Result<Unit>

    /**
     * 사용자 정보를 조회합니다.
     *
     * @param userId Firebase Authentication UID
     * @return Result<User> 성공 시 User 객체, 실패 시 에러
     */
    suspend fun getUser(userId: String): Result<User>

    /**
     * 사용자를 Soft Delete 처리합니다. isDeleted를 true로 설정하고 deletedAt에 현재 시간을 기록합니다.
     *
     * @param userId Firebase Authentication UID
     * @return Result<Unit> 성공 또는 실패
     */
    suspend fun softDeleteUser(userId: String): Result<Unit>

    /**
     * 사용자가 관리자인지 확인합니다.
     *
     * @param userId Firebase Authentication UID
     * @return Result<Boolean> 성공 시 관리자 여부, 실패 시 에러
     */
    suspend fun isAdmin(userId: String): Result<Boolean>
}
