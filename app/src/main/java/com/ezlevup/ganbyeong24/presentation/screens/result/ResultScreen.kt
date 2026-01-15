package com.ezlevup.ganbyeong24.presentation.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.components.GanbyeongButton
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 완료 화면
 *
 * 간병 신청 또는 간병사 등록 완료 후 표시되는 화면입니다.
 *
 * @param userRole 사용자 역할 ("guardian" 또는 "caregiver")
 * @param onConfirm 확인 버튼 클릭 시 호출되는 콜백
 */
@Composable
fun ResultScreen(userRole: String, onConfirm: () -> Unit) {
    val isGuardian = userRole == "guardian"

    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 성공 아이콘
            Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "완료",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(80.dp)
            )

            // 메시지
            Text(
                    text =
                            if (isGuardian) {
                                "간병 신청이\n완료되었습니다"
                            } else {
                                "간병사 등록이\n완료되었습니다"
                            },
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
            )

            // 안내 문구
            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                        text = "곧 담당자가 전화로\n연락드릴 예정입니다",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                )

                Text(
                        text = "연락처: 1234-5678",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 확인 버튼
            GanbyeongButton(text = "확인", onClick = onConfirm, modifier = Modifier.fillMaxWidth())
        }
    }
}

// ========== Preview ==========

@Preview(showBackground = true)
@Composable
private fun ResultScreenGuardianPreview() {
    GanbyeongTheme { ResultScreen(userRole = "guardian", onConfirm = {}) }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenCaregiverPreview() {
    GanbyeongTheme { ResultScreen(userRole = "caregiver", onConfirm = {}) }
}
