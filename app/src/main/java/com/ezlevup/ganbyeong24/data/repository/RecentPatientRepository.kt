package com.ezlevup.ganbyeong24.data.repository

import com.ezlevup.ganbyeong24.data.local.dao.RecentPatientDao
import com.ezlevup.ganbyeong24.data.local.entity.RecentPatient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 최근 신청 환자 Repository
 *
 * 최대 5개의 최근 환자명을 관리합니다.
 */
class RecentPatientRepository(private val recentPatientDao: RecentPatientDao) {
    companion object {
        private const val MAX_RECENT_PATIENTS = 5
    }

    /**
     * 최근 환자명 목록 조회 (Flow)
     *
     * @return 최근 환자명 목록
     */
    fun getRecentPatientNames(): Flow<List<String>> {
        return recentPatientDao.getRecentPatients(MAX_RECENT_PATIENTS).map { patients ->
            patients.map { it.patientName }
        }
    }

    /**
     * 환자명 저장 또는 업데이트
     *
     * 이미 존재하는 환자명이면 lastUsedAt만 업데이트 새 환자명이면 추가하고, 5개 초과 시 가장 오래된 것 삭제
     *
     * @param patientName 환자명
     */
    suspend fun savePatientName(patientName: String) {
        if (patientName.isBlank()) return

        // 기존 환자 찾기
        val existing = recentPatientDao.findByName(patientName)

        if (existing != null) {
            // 기존 환자면 lastUsedAt 업데이트
            recentPatientDao.update(existing.copy(lastUsedAt = System.currentTimeMillis()))
        } else {
            // 새 환자 추가
            recentPatientDao.insert(
                    RecentPatient(
                            patientName = patientName,
                            lastUsedAt = System.currentTimeMillis()
                    )
            )

            // 5개 초과 시 가장 오래된 것 삭제
            val count = recentPatientDao.getCount()
            if (count > MAX_RECENT_PATIENTS) {
                recentPatientDao.deleteOldest()
            }
        }
    }

    /**
     * 환자명 삭제
     *
     * @param patientName 환자명
     */
    suspend fun deletePatientName(patientName: String) {
        recentPatientDao.deleteByName(patientName)
    }

    /** 전체 삭제 */
    suspend fun deleteAll() {
        recentPatientDao.deleteAll()
    }
}
