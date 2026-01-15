package com.ezlevup.ganbyeong24.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongTextField
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import org.koin.androidx.compose.koinViewModel

/**
 * 로그인 화면
 *
 * @param viewModel 로그인 ViewModel
 * @param onLoginSuccess 로그인 성공 시 호출되는 콜백
 * @param onNavigateToSignup 회원가입 화면으로 이동하는 콜백
 */
@Composable
fun LoginScreen(
        viewModel: LoginViewModel = koinViewModel(),
        onLoginSuccess: () -> Unit,
        onNavigateToSignup: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // 로그인 성공 시 처리
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }

    Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        // 제목
        Text(
                text = "간병24",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
                text = "로그인하여 시작하세요",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 이메일
        GanbyeongTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = "이메일",
                placeholder = "example@email.com",
                isError = state.emailError != null,
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.padding(bottom = 16.dp)
        )

        // 비밀번호
        GanbyeongTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = "비밀번호",
                placeholder = "6자 이상",
                isError = state.passwordError != null,
                errorMessage = state.passwordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(bottom = 32.dp)
        )

        // 로그인 버튼
        GanbyeongButton(
                text = "로그인",
                onClick = viewModel::login,
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 회원가입 링크
        TextButton(onClick = onNavigateToSignup) {
            Text(text = "계정이 없으신가요? 회원가입", style = MaterialTheme.typography.bodyLarge)
        }
    }

    // 에러 다이얼로그
    if (state.errorMessage != null) {
        AlertDialog(
                onDismissRequest = viewModel::clearError,
                title = { Text("로그인 실패") },
                text = { Text(state.errorMessage!!) },
                confirmButton = { TextButton(onClick = viewModel::clearError) { Text("확인") } }
        )
    }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    GanbyeongTheme {
        LoginScreenContent(
                state = LoginState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLogin = {},
                onNavigateToSignup = {},
                onClearError = {}
        )
    }
}

@Composable
private fun LoginScreenContent(
        state: LoginState,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onLogin: () -> Unit,
        onNavigateToSignup: () -> Unit,
        onClearError: () -> Unit
) {
    Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = "간병24",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
                text = "로그인하여 시작하세요",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        GanbyeongTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = "이메일",
                placeholder = "example@email.com",
                isError = state.emailError != null,
                errorMessage = state.emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.padding(bottom = 16.dp)
        )

        GanbyeongTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "비밀번호",
                placeholder = "6자 이상",
                isError = state.passwordError != null,
                errorMessage = state.passwordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(bottom = 32.dp)
        )

        GanbyeongButton(
                text = "로그인",
                onClick = onLogin,
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToSignup) {
            Text(text = "계정이 없으신가요? 회원가입", style = MaterialTheme.typography.bodyLarge)
        }
    }

    if (state.errorMessage != null) {
        AlertDialog(
                onDismissRequest = onClearError,
                title = { Text("로그인 실패") },
                text = { Text(state.errorMessage) },
                confirmButton = { TextButton(onClick = onClearError) { Text("확인") } }
        )
    }
}
