package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.Match
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * 매칭 Repository 구현체
 *
 * Firestore를 사용하여 매칭 데이터를 관리합니다.
 *
 * @property firestore Firebase Firestore 인스턴스
 */
class MatchRepositoryImpl(private val firestore: FirebaseFirestore) : MatchRepository {

    companion object {
        private const val COLLECTION_NAME = "matches"
    }

    /**
     * 매칭을 생성합니다.
     *
     * @param match 생성할 매칭 정보
     * @return Result<String> 성공 시 생성된 문서 ID, 실패 시 에러
     */
    override suspend fun createMatch(match: Match): Result<String> {
        return try {
            val documentRef = firestore.collection(COLLECTION_NAME).add(match).await()
            Result.success(documentRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 매칭 상태를 업데이트합니다.
     *
     * @param matchId 매칭 문서 ID
     * @param status 새로운 상태
     * @return Result<Unit> 성공 시 Unit, 실패 시 에러
     */
    override suspend fun updateMatchStatus(matchId: String, status: String): Result<Unit> {
        return try {
            firestore
                    .collection(COLLECTION_NAME)
                    .document(matchId)
                    .update(mapOf("status" to status, "updatedAt" to Timestamp.now()))
                    .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 특정 매칭을 조회합니다.
     *
     * @param matchId 매칭 문서 ID
     * @return Result<Match> 성공 시 매칭 정보, 실패 시 에러
     */
    override suspend fun getMatchById(matchId: String): Result<Match> {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME).document(matchId).get().await()

            val match = snapshot.toObject(Match::class.java)?.copy(id = snapshot.id)
            if (match != null) {
                Result.success(match)
            } else {
                Result.failure(Exception("매칭을 찾을 수 없습니다"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 모든 매칭 목록을 조회합니다. (관리자 전용)
     *
     * @return Result<List<Match>> 성공 시 전체 매칭 목록, 실패 시 에러
     */
    override suspend fun getAllMatches(): Result<List<Match>> {
        return try {
            val snapshot =
                    firestore
                            .collection(COLLECTION_NAME)
                            .orderBy(
                                    "createdAt",
                                    com.google.firebase.firestore.Query.Direction.DESCENDING
                            )
                            .get()
                            .await()

            val matches =
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Match::class.java)?.copy(id = doc.id)
                    }

            Result.success(matches)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 매칭 일련번호를 생성합니다. Firestore Transaction을 사용하여 중복 없이 순차적으로 번호를 생성합니다.
     *
     * @return Result<Long> 성공 시 새로운 일련번호, 실패 시 에러
     */
    override suspend fun generateSerialNumber(): Result<Long> {
        return try {
            val counterRef = firestore.collection("counters").document("match_counter")

            val newNumber =
                    firestore
                            .runTransaction { transaction ->
                                val snapshot = transaction.get(counterRef)

                                // 문서가 없으면 생성 (시작 번호: 30000000000)
                                if (!snapshot.exists()) {
                                    transaction.set(counterRef, mapOf("value" to 30000000000L))
                                    30000000001L
                                } else {
                                    val currentValue = snapshot.getLong("value") ?: 30000000000L
                                    val newValue = currentValue + 1
                                    transaction.update(counterRef, "value", newValue)
                                    newValue
                                }
                            }
                            .await()

            Result.success(newNumber)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
