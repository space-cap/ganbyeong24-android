package com.ezlevup.ganbyeong24.presentation.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 관리자 대시보드 화면
 *
 * 관리자 전용 메뉴를 제공하는 화면입니다.
 *
 * @param onNavigateBack 뒤로가기 콜백
 * @param onNavigateToCareRequests 간병 신청 관리 화면으로 이동
 * @param onNavigateToCaregivers 간병사 관리 화면으로 이동
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
        onNavigateBack: () -> Unit = {},
        onNavigateToCareRequests: () -> Unit = {},
        onNavigateToCaregivers: () -> Unit = {}
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("관리자 메뉴") },
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
        Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            // 제목
            Text(
                    text = "관리자 대시보드",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 설명
            Text(
                    text = "관리 메뉴를 선택해주세요",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 48.dp)
            )

            // 간병 신청 관리 버튼
            AdminMenuButton(
                    icon = Icons.Default.List,
                    text = "간병 신청 관리",
                    description = "모든 간병 신청 내역 확인 및 관리",
                    onClick = onNavigateToCareRequests,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            // 간병사 관리 버튼
            AdminMenuButton(
                    icon = Icons.Default.Person,
                    text = "간병사 관리",
                    description = "등록된 간병사 목록 확인 및 관리",
                    onClick = onNavigateToCaregivers
            )
        }
    }
}

/**
 * 관리자 메뉴 버튼
 *
 * @param icon 버튼 아이콘
 * @param text 버튼 제목
 * @param description 버튼 설명
 * @param onClick 클릭 콜백
 * @param modifier Modifier
 */
@Composable
private fun AdminMenuButton(
        icon: ImageVector,
        text: String,
        description: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            colors =
                    CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                        text = text,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun AdminDashboardScreenPreview() {
    GanbyeongTheme { AdminDashboardScreen() }
}
