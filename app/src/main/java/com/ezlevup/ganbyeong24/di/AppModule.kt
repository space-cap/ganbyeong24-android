package com.ezlevup.ganbyeong24.di

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

/**
 * Koin 의존성 주입 모듈
 *
 * 앱에서 사용할 의존성들을 정의합니다.
 */
val appModule = module {

    // Firebase Firestore 인스턴스
    single { FirebaseFirestore.getInstance() }

    // TODO: Repository 추가 (4단계에서 구현)
    // single<CareRequestRepository> { CareRequestRepositoryImpl(get()) }
    // single<CaregiverRepository> { CaregiverRepositoryImpl(get()) }

    // TODO: ViewModel 추가 (4단계에서 구현)
    // viewModel { CareRequestViewModel(get()) }
    // viewModel { CaregiverViewModel(get()) }
}
