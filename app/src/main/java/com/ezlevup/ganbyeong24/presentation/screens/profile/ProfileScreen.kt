package com.ezlevup.ganbyeong24.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.compose.koinViewModel

/**
 * 프로필/설정 화면
 *
 * 사용자 정보를 표시하고 로그아웃 및 회원 탈퇴 기능을 제공합니다.
 *
 * @param onNavigateBack 뒤로가기 콜백
 * @param onLogoutSuccess 로그아웃 성공 시 콜백
 * @param onDeleteSuccess 회원 탈퇴 성공 시 콜백
 * @param viewModel ProfileViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
        onNavigateBack: () -> Unit,
        onLogoutSuccess: () -> Unit,
        onDeleteSuccess: () -> Unit,
        viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // 로그아웃 성공 시 콜백 호출
    LaunchedEffect(state.isLogoutSuccess) {
        if (state.isLogoutSuccess) {
            onLogoutSuccess()
        }
    }

    // 회원 탈퇴 성공 시 콜백 호출
    LaunchedEffect(state.isDeleteSuccess) {
        if (state.isDeleteSuccess) {
            onDeleteSuccess()
        }
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("프로필 및 설정") },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "뒤로가기"
                                )
                            }
                        }
                )
            }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // 사용자 정보 섹션
                    Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors =
                                    CardDefaults.cardColors(
                                            containerColor =
                                                    MaterialTheme.colorScheme.surfaceVariant
                                    )
                    ) {
                        Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = "계정 정보", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                            Divider()

                            // 이메일
                            Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                        text = "이메일",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                        text = state.email,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                )
                            }

                            // 가입일
                            state.createdAt?.let { timestamp ->
                                val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN)
                                val dateString = dateFormat.format(timestamp.toDate())

                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                            text = "가입일",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                            text = dateString,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // 로그아웃 버튼
                    GanbyeongButton(
                            text = "로그아웃",
                            onClick = { viewModel.logout() },
                            modifier = Modifier.fillMaxWidth()
                    )

                    // 회원 탈퇴 버튼
                    OutlinedButton(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors =
                                    ButtonDefaults.outlinedButtonColors(
                                            contentColor = MaterialTheme.colorScheme.error
                                    )
                    ) { Text(text = "회원 탈퇴", fontSize = 18.sp, fontWeight = FontWeight.Bold) }

                    // 앱 버전 정보
                    Text(
                            text = "버전 1.0.0",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        // 회원 탈퇴 확인 다이얼로그
        if (showDeleteDialog) {
            AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = {
                        Text(text = "회원 탈퇴", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Text(
                                text = "정말로 탈퇴하시겠습니까?\n\n탈퇴 시 모든 데이터가 삭제되며 복구할 수 없습니다.",
                                fontSize = 16.sp
                        )
                    },
                    confirmButton = {
                        TextButton(
                                onClick = {
                                    showDeleteDialog = false
                                    viewModel.deleteAccount()
                                },
                                colors =
                                        ButtonDefaults.textButtonColors(
                                                contentColor = MaterialTheme.colorScheme.error
                                        )
                        ) { Text(text = "탈퇴", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text(text = "취소", fontSize = 16.sp)
                        }
                    }
            )
        }

        // 에러 다이얼로그
        state.error?.let { errorMessage ->
            AlertDialog(
                    onDismissRequest = { viewModel.clearError() },
                    title = { Text(text = "오류", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                    text = { Text(text = errorMessage, fontSize = 16.sp) },
                    confirmButton = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text(text = "확인", fontSize = 16.sp)
                        }
                    }
            )
        }
    }
}
