package com.ezlevup.ganbyeong24.presentation.screens.role

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
 * 역할 선택 화면
 *
 * 사용자가 보호자 또는 간병사 역할을 선택할 수 있는 화면입니다.
 *
 * @param onGuardianSelected 보호자 선택 시 호출되는 콜백
 * @param onCaregiverSelected 간병사 선택 시 호출되는 콜백
 * @param onNavigateToProfile 프로필/설정 화면으로 이동하는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
        onGuardianSelected: () -> Unit,
        onCaregiverSelected: () -> Unit,
        onNavigateToProfile: () -> Unit = {}
) {
        Scaffold(
                topBar = {
                        TopAppBar(
                                title = { Text("간병24") },
                                actions = {
                                        IconButton(onClick = onNavigateToProfile) {
                                                Icon(
                                                        imageVector = Icons.Default.Settings,
                                                        contentDescription = "설정"
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
                                text = "간병24에 오신 것을\n환영합니다",
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 설명
                        Text(
                                text = "원하시는 서비스를 선택해주세요",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 48.dp)
                        )

                        // 보호자 버튼
                        RoleButton(
                                icon = Icons.Default.Favorite,
                                text = "간병이 필요해요",
                                onClick = onGuardianSelected,
                                modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 간병사 버튼
                        RoleButton(
                                icon = Icons.Default.Person,
                                text = "간병사로 등록할게요",
                                onClick = onCaregiverSelected
                        )
                }
        }
}

/**
 * 역할 선택 버튼
 *
 * 아이콘과 텍스트를 포함한 큰 버튼 컴포넌트입니다.
 *
 * @param icon 버튼에 표시할 아이콘
 * @param text 버튼에 표시할 텍스트
 * @param onClick 버튼 클릭 시 호출되는 콜백
 * @param modifier Modifier
 */
@Composable
private fun RoleButton(
        icon: ImageVector,
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
        Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth().height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors =
                        ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                        )
        ) {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                ) {
                        Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = text, style = MaterialTheme.typography.headlineMedium)
                }
        }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun RoleSelectionScreenPreview() {
        GanbyeongTheme {
                RoleSelectionScreen(
                        onGuardianSelected = {},
                        onCaregiverSelected = {},
                        onNavigateToProfile = {}
                )
        }
}
