package com.ezlevup.ganbyeong24.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ezlevup.ganbyeong24.data.repository.AuthRepository
import com.ezlevup.ganbyeong24.presentation.screens.auth.LoginScreen
import com.ezlevup.ganbyeong24.presentation.screens.auth.SignupScreen
import com.ezlevup.ganbyeong24.presentation.screens.care_request.CareRequestScreen
import com.ezlevup.ganbyeong24.presentation.screens.caregiver.CaregiverRegistrationScreen
import com.ezlevup.ganbyeong24.presentation.screens.result.ResultScreen
import com.ezlevup.ganbyeong24.presentation.screens.role.RoleSelectionScreen
import com.ezlevup.ganbyeong24.presentation.screens.splash.SplashScreen
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

/**
 * 앱의 Navigation Graph
 *
 * 모든 화면과 화면 간 이동을 관리합니다.
 *
 * @param navController Navigation을 제어하는 NavHostController
 */
@Composable
fun GanbyeongNavGraph(
        navController: NavHostController = rememberNavController(),
        authRepository: AuthRepository = koinInject()
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        // 스플래시 화면
        composable(Screen.Splash.route) {
            val isLoggedIn = authRepository.isLoggedIn()

            SplashScreen(
                    onNavigateToRoleSelection = {
                        val destination =
                                if (isLoggedIn) {
                                    Screen.RoleSelection.route
                                } else {
                                    Screen.Login.route
                                }
                        navController.navigate(destination) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
            )
        }

        // 로그인 화면
        composable(Screen.Login.route) {
            LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.RoleSelection.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignup = { navController.navigate(Screen.Signup.route) }
            )
        }

        // 회원가입 화면
        composable(Screen.Signup.route) {
            SignupScreen(
                    onSignupSuccess = {
                        navController.navigate(Screen.RoleSelection.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateBack = { navController.popBackStack() }
            )
        }

        // 역할 선택 화면
        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                    onGuardianSelected = { navController.navigate(Screen.CareRequest.route) },
                    onCaregiverSelected = {
                        navController.navigate(Screen.CaregiverRegistration.route)
                    }
            )
        }

        // 간병 신청 화면
        composable(Screen.CareRequest.route) {
            CareRequestScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate(Screen.Result.createRoute("guardian")) {
                            popUpTo(Screen.RoleSelection.route)
                        }
                    }
            )
        }

        // 간병사 등록 화면
        composable(Screen.CaregiverRegistration.route) {
            CaregiverRegistrationScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate(Screen.Result.createRoute("caregiver")) {
                            popUpTo(Screen.RoleSelection.route)
                        }
                    }
            )
        }

        // 결과 화면
        composable(
                route = Screen.Result.route,
                arguments = listOf(navArgument("userRole") { type = NavType.StringType })
        ) { backStackEntry ->
            val userRole = backStackEntry.arguments?.getString("userRole") ?: "unknown"
            ResultScreen(
                    userRole = userRole,
                    onConfirm = {
                        navController.navigate(Screen.RoleSelection.route) {
                            popUpTo(Screen.RoleSelection.route) { inclusive = true }
                        }
                    }
            )
        }
    }
}

// ========== 임시 화면들 (3단계에서 실제 화면으로 교체) ==========

@Composable
private fun SplashScreenPlaceholder(onNavigateToRoleSelection: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // 2초 대기
        onNavigateToRoleSelection()
    }

    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "💙", style = MaterialTheme.typography.headlineLarge)
            Text(
                    text = "간병24",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
            )
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun RoleSelectionScreenPlaceholder(
        onGuardianClick: () -> Unit,
        onCaregiverClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "역할 선택", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onGuardianClick, modifier = Modifier.fillMaxWidth()) {
                Text("보호자 (간병 신청)")
            }
            Button(onClick = onCaregiverClick, modifier = Modifier.fillMaxWidth()) {
                Text("간병사 (등록)")
            }
        }
    }
}

@Composable
private fun CareRequestScreenPlaceholder(onSubmitSuccess: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "간병 신청 화면", style = MaterialTheme.typography.headlineLarge)
            Text("(3단계에서 개발 예정)")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onSubmitSuccess, modifier = Modifier.fillMaxWidth()) { Text("신청 완료") }
        }
    }
}

@Composable
private fun CaregiverRegistrationScreenPlaceholder(onSubmitSuccess: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "간병사 등록 화면", style = MaterialTheme.typography.headlineLarge)
            Text("(3단계에서 개발 예정)")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onSubmitSuccess, modifier = Modifier.fillMaxWidth()) { Text("등록 완료") }
        }
    }
}

@Composable
private fun ResultScreenPlaceholder(userRole: String, onFinish: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "✅", style = MaterialTheme.typography.headlineLarge)
            Text(
                    text = if (userRole == "guardian") "신청 완료!" else "등록 완료!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
            )
            Text("담당자가 곧 연락드리겠습니다")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) { Text("처음으로") }
        }
    }
}
