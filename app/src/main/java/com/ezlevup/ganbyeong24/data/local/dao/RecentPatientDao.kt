package com.ezlevup.ganbyeong24.data.local.dao

import androidx.room.*
import com.ezlevup.ganbyeong24.data.local.entity.RecentPatient
import kotlinx.coroutines.flow.Flow

/** 최근 신청 환자 데이터 접근 객체 */
@Dao
interface RecentPatientDao {

    /**
     * 최근 사용 순으로 환자 목록 조회
     *
     * @param limit 조회할 최대 개수
     * @return 최근 환자 목록 (Flow)
     */
    @Query("SELECT * FROM recent_patients ORDER BY lastUsedAt DESC LIMIT :limit")
    fun getRecentPatients(limit: Int = 5): Flow<List<RecentPatient>>

    /**
     * 환자명으로 검색
     *
     * @param patientName 환자명
     * @return 환자 정보 (nullable)
     */
    @Query("SELECT * FROM recent_patients WHERE patientName = :patientName LIMIT 1")
    suspend fun findByName(patientName: String): RecentPatient?

    /**
     * 환자 정보 추가
     *
     * @param patient 환자 정보
     * @return 생성된 ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: RecentPatient): Long

    /**
     * 환자 정보 업데이트
     *
     * @param patient 환자 정보
     */
    @Update suspend fun update(patient: RecentPatient)

    /**
     * 환자 정보 삭제
     *
     * @param id 환자 ID
     */
    @Query("DELETE FROM recent_patients WHERE id = :id") suspend fun deleteById(id: Long)

    /**
     * 환자명으로 삭제
     *
     * @param patientName 환자명
     */
    @Query("DELETE FROM recent_patients WHERE patientName = :patientName")
    suspend fun deleteByName(patientName: String)

    /** 가장 오래된 환자 정보 삭제 */
    @Query(
            "DELETE FROM recent_patients WHERE id IN (SELECT id FROM recent_patients ORDER BY lastUsedAt ASC LIMIT 1)"
    )
    suspend fun deleteOldest()

    /**
     * 전체 환자 수 조회
     *
     * @return 환자 수
     */
    @Query("SELECT COUNT(*) FROM recent_patients") suspend fun getCount(): Int

    /** 전체 삭제 */
    @Query("DELETE FROM recent_patients") suspend fun deleteAll()
}
