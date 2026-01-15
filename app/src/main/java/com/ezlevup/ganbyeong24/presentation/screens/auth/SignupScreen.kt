package com.ezlevup.ganbyeong24.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
 * 회원가입 화면
 *
 * @param viewModel 회원가입 ViewModel
 * @param onSignupSuccess 회원가입 성공 시 호출되는 콜백
 * @param onNavigateBack 뒤로가기 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
        viewModel: SignupViewModel = koinViewModel(),
        onSignupSuccess: () -> Unit,
        onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // 회원가입 성공 시 처리
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onSignupSuccess()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("회원가입") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                            }
                        }
                )
            }
    ) { padding ->
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(padding)
                                .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    text = "간병24 계정 만들기",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 32.dp)
            )

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
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 비밀번호 확인
            GanbyeongTextField(
                    value = state.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = "비밀번호 확인",
                    placeholder = "비밀번호를 다시 입력하세요",
                    isError = state.confirmPasswordError != null,
                    errorMessage = state.confirmPasswordError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            // 회원가입 버튼
            GanbyeongButton(
                    text = "회원가입",
                    onClick = viewModel::signup,
                    isLoading = state.isLoading,
                    modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // 에러 다이얼로그
    if (state.errorMessage != null) {
        AlertDialog(
                onDismissRequest = viewModel::clearError,
                title = { Text("회원가입 실패") },
                text = { Text(state.errorMessage!!) },
                confirmButton = { TextButton(onClick = viewModel::clearError) { Text("확인") } }
        )
    }
}

// ========== Preview ==========

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SignupScreenPreview() {
    GanbyeongTheme {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = { Text("회원가입") },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                                }
                            }
                    )
                }
        ) { padding ->
            Column(
                    modifier =
                            Modifier.fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(padding)
                                    .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                        text = "간병24 계정 만들기",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 32.dp)
                )

                GanbyeongTextField(
                        value = "",
                        onValueChange = {},
                        label = "이메일",
                        placeholder = "example@email.com",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.padding(bottom = 16.dp)
                )

                GanbyeongTextField(
                        value = "",
                        onValueChange = {},
                        label = "비밀번호",
                        placeholder = "6자 이상",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(bottom = 16.dp)
                )

                GanbyeongTextField(
                        value = "",
                        onValueChange = {},
                        label = "비밀번호 확인",
                        placeholder = "비밀번호를 다시 입력하세요",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(bottom = 32.dp)
                )

                GanbyeongButton(text = "회원가입", onClick = {}, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
