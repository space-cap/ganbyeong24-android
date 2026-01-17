package com.ezlevup.ganbyeong24.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * 인증 Repository 구현체
 *
 * Firebase Authentication을 사용하여 사용자 인증을 처리합니다.
 *
 * @property auth Firebase Authentication 인스턴스
 */
class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    /**
     * 이메일과 비밀번호로 로그인합니다.
     *
     * @param email 이메일
     * @param password 비밀번호
     * @return Result<String> 성공 시 사용자 ID, 실패 시 에러
     */
    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("사용자 ID를 가져올 수 없습니다")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 이메일과 비밀번호로 회원가입합니다.
     *
     * @param email 이메일
     * @param password 비밀번호
     * @return Result<String> 성공 시 사용자 ID, 실패 시 에러
     */
    override suspend fun signup(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("사용자 ID를 가져올 수 없습니다")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** 로그아웃합니다. */
    override fun logout() {
        auth.signOut()
    }

    /**
     * 현재 로그인한 사용자의 ID를 반환합니다.
     *
     * @return 사용자 ID, 로그인하지 않은 경우 null
     */
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    /**
     * 현재 사용자가 로그인했는지 확인합니다.
     *
     * @return 로그인 여부
     */
    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * 현재 로그인한 사용자의 이메일을 반환합니다.
     *
     * @return 사용자 이메일, 로그인하지 않은 경우 null
     */
    override fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    /**
     * 현재 로그인한 사용자의 Firebase Auth 계정을 삭제합니다.
     *
     * @return Result<Unit> 성공 또는 실패
     */
    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            auth.currentUser?.delete()?.await() ?: throw Exception("로그인된 사용자가 없습니다")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
