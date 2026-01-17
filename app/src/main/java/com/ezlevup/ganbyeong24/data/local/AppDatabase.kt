package com.ezlevup.ganbyeong24.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ezlevup.ganbyeong24.data.local.dao.RecentPatientDao
import com.ezlevup.ganbyeong24.data.local.entity.RecentPatient

/**
 * 앱의 Room Database
 *
 * @property recentPatientDao 최근 환자 DAO
 */
@Database(entities = [RecentPatient::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recentPatientDao(): RecentPatientDao

    companion object {
        private const val DATABASE_NAME = "ganbyeong24_database"

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE
                    ?: synchronized(this) {
                        val instance =
                                Room.databaseBuilder(
                                                context.applicationContext,
                                                AppDatabase::class.java,
                                                DATABASE_NAME
                                        )
                                        .build()
                        INSTANCE = instance
                        instance
                    }
        }
    }
}
