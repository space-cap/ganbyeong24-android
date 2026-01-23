package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.CareRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * 간병 신청 Repository 구현체
 *
 * Firestore를 사용하여 간병 신청 데이터를 저장합니다.
 *
 * @property firestore Firebase Firestore 인스턴스
 */
class CareRequestRepositoryImpl(private val firestore: FirebaseFirestore) : CareRequestRepository {

    companion object {
        private const val COLLECTION_NAME = "care_requests"
    }

    /**
     * 간병 신청 정보를 Firestore에 저장합니다.
     *
     * @param request 저장할 간병 신청 정보
     * @return Result<String> 성공 시 생성된 문서 ID, 실패 시 에러
     */
    override suspend fun saveCareRequest(request: CareRequest): Result<String> {
        return try {
            // Firestore에 새 문서 추가
            val documentRef = firestore.collection(COLLECTION_NAME).add(request).await()

            // 생성된 문서 ID 반환
            Result.success(documentRef.id)
        } catch (e: Exception) {
            // 에러 발생 시 Result.failure 반환
            Result.failure(e)
        }
    }

    /**
     * 특정 사용자의 간병 신청 목록을 조회합니다.
     *
     * @param userId 사용자 ID (Firebase Auth)
     * @return Result<List<CareRequest>> 성공 시 신청 목록, 실패 시 에러
     */
    override suspend fun getCareRequestsByUserId(userId: String): Result<List<CareRequest>> {
        return try {
            val snapshot =
                    firestore
                            .collection(COLLECTION_NAME)
                            .whereEqualTo("userId", userId)
                            .orderBy(
                                    "createdAt",
                                    com.google.firebase.firestore.Query.Direction.DESCENDING
                            )
                            .get()
                            .await()

            val careRequests =
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(CareRequest::class.java)?.copy(id = doc.id)
                    }

            Result.success(careRequests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 모든 간병 신청 목록을 조회합니다. (관리자 전용)
     *
     * @return Result<List<CareRequest>> 성공 시 전체 신청 목록, 실패 시 에러
     */
    override suspend fun getAllCareRequests(): Result<List<CareRequest>> {
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

            val careRequests =
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(CareRequest::class.java)?.copy(id = doc.id)
                    }

            Result.success(careRequests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 간병 신청 일련번호를 생성합니다. Firestore Transaction을 사용하여 중복 없이 순차적으로 번호를 생성합니다.
     *
     * @return Result<Int> 성공 시 새로운 일련번호, 실패 시 에러
     */
    override suspend fun generateSerialNumber(): Result<Int> {
        return try {
            val counterRef = firestore.collection("counters").document("care_request_counter")

            val newNumber =
                    firestore
                            .runTransaction { transaction ->
                                val snapshot = transaction.get(counterRef)

                                // 문서가 없으면 생성 (시작 번호: 1000)
                                if (!snapshot.exists()) {
                                    transaction.set(counterRef, mapOf("value" to 1000))
                                    1001
                                } else {
                                    val currentValue = snapshot.getLong("value") ?: 1000
                                    val newValue = currentValue + 1
                                    transaction.update(counterRef, "value", newValue)
                                    newValue.toInt()
                                }
                            }
                            .await()

            Result.success(newNumber)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
