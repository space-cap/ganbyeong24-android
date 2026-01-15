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
}
