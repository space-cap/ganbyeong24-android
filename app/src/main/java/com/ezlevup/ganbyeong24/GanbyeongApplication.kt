package com.ezlevup.ganbyeong24

import android.app.Application
import com.ezlevup.ganbyeong24.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 간병24 Application 클래스
 *
 * Koin 의존성 주입을 초기화합니다.
 */
class GanbyeongApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin 초기화
        startKoin {
            // Koin 로그 레벨 설정 (개발 중에는 DEBUG, 릴리스에서는 ERROR)
            androidLogger(Level.DEBUG)

            // Android Context 제공
            androidContext(this@GanbyeongApplication)

            // 모듈 등록
            modules(appModule)
        }
    }
}
