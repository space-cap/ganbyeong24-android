package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.CareRequest

/**
 * 간병 신청 Repository 인터페이스
 *
 * 간병 신청 데이터의 저장 및 조회를 담당합니다.
 */
interface CareRequestRepository {
    /**
     * 간병 신청 정보를 저장합니다.
     *
     * @param request 저장할 간병 신청 정보
     * @return Result<String> 성공 시 문서 ID, 실패 시 에러
     */
    suspend fun saveCareRequest(request: CareRequest): Result<String>
}
