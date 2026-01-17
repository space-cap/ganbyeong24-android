package com.ezlevup.ganbyeong24.di

import com.ezlevup.ganbyeong24.data.local.AppDatabase
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.data.repository.AuthRepositoryImpl
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepository
import com.ezlevup.ganbyeong24.data.repository.CareRequestRepositoryImpl
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepository
import com.ezlevup.ganbyeong24.data.repository.CaregiverRepositoryImpl
import com.ezlevup.ganbyeong24.data.repository.RecentPatientRepository
import com.ezlevup.ganbyeong24.data.repository.UserRepository
import com.ezlevup.ganbyeong24.data.repository.UserRepositoryImpl
import com.ezlevup.ganbyeong24.presentation.screens.auth.LoginViewModel
import com.ezlevup.ganbyeong24.presentation.screens.auth.SignupViewModel
import com.ezlevup.ganbyeong24.presentation.screens.care_request.CareRequestViewModel
import com.ezlevup.ganbyeong24.presentation.screens.caregiver.CaregiverRegistrationViewModel
import com.ezlevup.ganbyeong24.presentation.screens.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin 의존성 주입 모듈
 *
 * 앱에서 사용할 의존성들을 정의합니다.
 */
val appModule = module {

    // Firebase 인스턴스
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }

    // Room Database
    single { AppDatabase.getInstance(androidContext()) }
    single { get<AppDatabase>().recentPatientDao() }

    // Repository
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CareRequestRepository> { CareRequestRepositoryImpl(get()) }
    single<CaregiverRepository> { CaregiverRepositoryImpl(get()) }
    single { RecentPatientRepository(get()) }

    // ViewModel
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignupViewModel(get(), get()) }
    viewModel { CareRequestViewModel(get(), get(), get()) }
    viewModel { CaregiverRegistrationViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}
