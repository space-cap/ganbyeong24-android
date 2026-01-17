package com.ezlevup.ganbyeong24.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 최근 신청한 환자 정보를 저장하는 Entity
 *
 * @property id Primary Key (자동 생성)
 * @property patientName 환자명
 * @property lastUsedAt 마지막 사용 시간 (밀리초)
 */
@Entity(tableName = "recent_patients")
data class RecentPatient(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val patientName: String,
        val lastUsedAt: Long = System.currentTimeMillis()
)
