package com.ezlevup.ganbyeong24.di

import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepositoryImpl
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepository
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepositoryImpl
import com.ezlevup.ganbyeong24.presentation.screens.care_request.CareRequestViewModel
import com.ezlevup.ganbyeong24.presentation.screens.caregiver.CaregiverRegistrationViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin 의존성 주입 모듈
 *
 * 앱에서 사용할 의존성들을 정의합니다.
 */
val appModule = module {

    // Firebase Firestore 인스턴스
    single { FirebaseFirestore.getInstance() }

    // Repository
    single<CareRequestRepository> { CareRequestRepositoryImpl(get()) }
    single<CaregiverRepository> { CaregiverRepositoryImpl(get()) }

    // ViewModel
    viewModel { CareRequestViewModel(get()) }
    viewModel { CaregiverRegistrationViewModel(get()) }
}
