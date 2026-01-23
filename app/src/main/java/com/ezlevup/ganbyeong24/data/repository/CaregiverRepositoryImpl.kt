package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.Caregiver
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * 간병사 Repository 구현체
 *
 * Firestore를 사용하여 간병사 데이터를 저장합니다.
 *
 * @property firestore Firebase Firestore 인스턴스
 */
class CaregiverRepositoryImpl(private val firestore: FirebaseFirestore) : CaregiverRepository {

    companion object {
        private const val COLLECTION_NAME = "caregivers"
    }

    /**
     * 간병사 정보를 Firestore에 저장합니다.
     *
     * @param caregiver 저장할 간병사 정보
     * @return Result<String> 성공 시 생성된 문서 ID, 실패 시 에러
     */
    override suspend fun saveCaregiver(caregiver: Caregiver): Result<String> {
        return try {
            // Firestore에 새 문서 추가
            val documentRef = firestore.collection(COLLECTION_NAME).add(caregiver).await()

            // 생성된 문서 ID 반환
            Result.success(documentRef.id)
        } catch (e: Exception) {
            // 에러 발생 시 Result.failure 반환
            Result.failure(e)
        }
    }

    /**
     * 모든 간병사 목록을 조회합니다. (관리자 전용)
     *
     * @return Result<List<Caregiver>> 성공 시 전체 간병사 목록, 실패 시 에러
     */
    override suspend fun getAllCaregivers(): Result<List<Caregiver>> {
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

            val caregivers =
                    snapshot.documents.mapNotNull { doc ->
                        try {
                            doc.toObject(Caregiver::class.java)?.copy(id = doc.id)
                        } catch (e: Exception) {
                            // 역직렬화 오류 발생 시 해당 문서 건너뛰기
                            android.util.Log.e(
                                    "CaregiverRepository",
                                    "Failed to deserialize caregiver ${doc.id}: ${e.message}"
                            )
                            null
                        }
                    }

            Result.success(caregivers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
