package com.ezlevup.ganbyeong24.presentation.screens.caregiver

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongTextField
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import org.koin.androidx.compose.koinViewModel

/**
 * 간병사 등록 화면
 *
 * 간병사가 자신의 정보를 등록할 수 있는 화면입니다.
 *
 * @param viewModel 간병사 등록 ViewModel
 * @param onNavigateBack 뒤로가기 콜백
 * @param onSuccess 등록 성공 시 호출되는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaregiverRegistrationScreen(
        viewModel: CaregiverRegistrationViewModel = koinViewModel(),
        onNavigateBack: () -> Unit = {},
        onSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // 등록 성공 시 ResultScreen으로 이동
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onSuccess()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병사 등록") },
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
                                .padding(padding)
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp)
        ) {
            // 이름
            GanbyeongTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    label = "이름 *",
                    placeholder = "예: 김영희",
                    isError = state.nameError != null,
                    errorMessage = state.nameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 경력
            GanbyeongTextField(
                    value = state.experience,
                    onValueChange = viewModel::onExperienceChange,
                    label = "경력 *",
                    placeholder = "예: 5년",
                    isError = state.experienceError != null,
                    errorMessage = state.experienceError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 자격증
            GanbyeongTextField(
                    value = state.certificates,
                    onValueChange = viewModel::onCertificatesChange,
                    label = "자격증 *",
                    placeholder = "예: 요양보호사",
                    isError = state.certificatesError != null,
                    errorMessage = state.certificatesError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 가능 지역
            GanbyeongTextField(
                    value = state.availableRegions,
                    onValueChange = viewModel::onAvailableRegionsChange,
                    label = "가능 지역 *",
                    placeholder = "예: 서울, 경기",
                    isError = state.availableRegionsError != null,
                    errorMessage = state.availableRegionsError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 연락처
            GanbyeongTextField(
                    value = state.phoneNumber,
                    onValueChange = viewModel::onPhoneNumberChange,
                    label = "연락처 *",
                    placeholder = "010-1111-2222",
                    isError = state.phoneNumberError != null,
                    errorMessage = state.phoneNumberError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            // 등록 버튼
            GanbyeongButton(
                    text = "등록하기",
                    onClick = viewModel::registerCaregiver,
                    isLoading = state.isLoading
            )
        }
    }

    // 에러 다이얼로그
    if (state.errorMessage != null) {
        AlertDialog(
                onDismissRequest = viewModel::clearError,
                title = { Text("오류") },
                text = { Text(state.errorMessage!!) },
                confirmButton = { TextButton(onClick = viewModel::clearError) { Text("확인") } }
        )
    }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun CaregiverRegistrationScreenPreview() {
    GanbyeongTheme {
        CaregiverRegistrationScreenContent(
                state = CaregiverRegistrationState(),
                onNameChange = {},
                onExperienceChange = {},
                onCertificatesChange = {},
                onAvailableRegionsChange = {},
                onPhoneNumberChange = {},
                onSubmit = {},
                onNavigateBack = {},
                onClearError = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CaregiverRegistrationScreenContent(
        state: CaregiverRegistrationState,
        onNameChange: (String) -> Unit,
        onExperienceChange: (String) -> Unit,
        onCertificatesChange: (String) -> Unit,
        onAvailableRegionsChange: (String) -> Unit,
        onPhoneNumberChange: (String) -> Unit,
        onSubmit: () -> Unit,
        onNavigateBack: () -> Unit,
        onClearError: () -> Unit
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("간병사 등록") },
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
                                .padding(padding)
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp)
        ) {
            GanbyeongTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = "이름 *",
                    placeholder = "예: 김영희",
                    isError = state.nameError != null,
                    errorMessage = state.nameError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.experience,
                    onValueChange = onExperienceChange,
                    label = "경력 *",
                    placeholder = "예: 5년",
                    isError = state.experienceError != null,
                    errorMessage = state.experienceError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.certificates,
                    onValueChange = onCertificatesChange,
                    label = "자격증 *",
                    placeholder = "예: 요양보호사",
                    isError = state.certificatesError != null,
                    errorMessage = state.certificatesError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.availableRegions,
                    onValueChange = onAvailableRegionsChange,
                    label = "가능 지역 *",
                    placeholder = "예: 서울, 경기",
                    isError = state.availableRegionsError != null,
                    errorMessage = state.availableRegionsError,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            GanbyeongTextField(
                    value = state.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    label = "연락처 *",
                    placeholder = "010-1111-2222",
                    isError = state.phoneNumberError != null,
                    errorMessage = state.phoneNumberError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            GanbyeongButton(text = "등록하기", onClick = onSubmit, isLoading = state.isLoading)
        }
    }

    if (state.errorMessage != null) {
        AlertDialog(
                onDismissRequest = onClearError,
                title = { Text("오류") },
                text = { Text(state.errorMessage) },
                confirmButton = { TextButton(onClick = onClearError) { Text("확인") } }
        )
    }
}
