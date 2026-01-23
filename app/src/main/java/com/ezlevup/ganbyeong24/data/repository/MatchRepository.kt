package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.model.Match

/**
 * 매칭 Repository 인터페이스
 *
 * 매칭 데이터의 저장, 조회, 업데이트를 담당합니다.
 */
interface MatchRepository {
    /**
     * 매칭을 생성합니다.
     *
     * @param match 생성할 매칭 정보
     * @return Result<String> 성공 시 문서 ID, 실패 시 에러
     */
    suspend fun createMatch(match: Match): Result<String>

    /**
     * 매칭 상태를 업데이트합니다.
     *
     * @param matchId 매칭 문서 ID
     * @param status 새로운 상태
     * @return Result<Unit> 성공 시 Unit, 실패 시 에러
     */
    suspend fun updateMatchStatus(matchId: String, status: String): Result<Unit>

    /**
     * 특정 매칭을 조회합니다.
     *
     * @param matchId 매칭 문서 ID
     * @return Result<Match> 성공 시 매칭 정보, 실패 시 에러
     */
    suspend fun getMatchById(matchId: String): Result<Match>

    /**
     * 모든 매칭 목록을 조회합니다. (관리자 전용)
     *
     * @return Result<List<Match>> 성공 시 전체 매칭 목록, 실패 시 에러
     */
    suspend fun getAllMatches(): Result<List<Match>>

    /**
     * 매칭 일련번호를 생성합니다.
     *
     * @return Result<Long> 성공 시 새로운 일련번호, 실패 시 에러
     */
    suspend fun generateSerialNumber(): Result<Long>
}
