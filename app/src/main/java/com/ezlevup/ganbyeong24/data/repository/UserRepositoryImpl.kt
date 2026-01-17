package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * 사용자 정보 Repository 구현체
 *
 * Firestore의 users 컬렉션을 사용하여 사용자 정보를 관리합니다.
 *
 * @property firestore Firestore 인스턴스
 */
class UserRepositoryImpl(private val firestore: FirebaseFirestore) : UserRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
    }

    /**
     * 새로운 사용자를 생성합니다.
     *
     * @param userId Firebase Authentication UID
     * @param email 사용자 이메일
     * @return Result<Unit> 성공 또는 실패
     */
    override suspend fun createUser(userId: String, email: String): Result<Unit> {
        return try {
            val user =
                    User(
                            userId = userId,
                            email = email,
                            createdAt = Timestamp.now(),
                            isDeleted = false,
                            deletedAt = null
                    )
            firestore.collection(COLLECTION_USERS).document(userId).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 사용자 정보를 조회합니다.
     *
     * @param userId Firebase Authentication UID
     * @return Result<User> 성공 시 User 객체, 실패 시 에러
     */
    override suspend fun getUser(userId: String): Result<User> {
        return try {
            val snapshot = firestore.collection(COLLECTION_USERS).document(userId).get().await()

            val user = snapshot.toObject(User::class.java) ?: throw Exception("사용자 정보를 찾을 수 없습니다")

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 사용자를 Soft Delete 처리합니다. isDeleted를 true로 설정하고 deletedAt에 현재 시간을 기록합니다.
     *
     * @param userId Firebase Authentication UID
     * @return Result<Unit> 성공 또는 실패
     */
    override suspend fun softDeleteUser(userId: String): Result<Unit> {
        return try {
            val updates =
                    hashMapOf<String, Any>("isDeleted" to true, "deletedAt" to Timestamp.now())

            firestore.collection(COLLECTION_USERS).document(userId).update(updates).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
