package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.Caregiver

/**
 * 간병사 Repository 인터페이스
 *
 * 간병사 데이터의 저장 및 조회를 담당합니다.
 */
interface CaregiverRepository {
    /**
     * 간병사 정보를 저장합니다.
     *
     * @param caregiver 저장할 간병사 정보
     * @return Result<String> 성공 시 문서 ID, 실패 시 에러
     */
    suspend fun saveCaregiver(caregiver: Caregiver): Result<String>
}
