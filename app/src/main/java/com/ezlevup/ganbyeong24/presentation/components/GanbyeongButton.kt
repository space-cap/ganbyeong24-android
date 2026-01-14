package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 간병24 앱의 공통 버튼 컴포넌트
 *
 * 시니어 친화적인 큰 크기의 버튼으로, 로딩 상태를 지원합니다.
 *
 * @param text 버튼에 표시할 텍스트
 * @param onClick 버튼 클릭 시 실행할 함수
 * @param modifier Modifier
 * @param enabled 버튼 활성화 여부 (기본값: true)
 * @param isLoading 로딩 상태 여부 (기본값: false)
 */
@Composable
fun GanbyeongButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        isLoading: Boolean = false
) {
    Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth().height(56.dp),
            enabled = enabled && !isLoading,
            shape = RoundedCornerShape(8.dp),
            colors =
                    ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                    )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
            )
        } else {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GanbyeongButtonPreview() {
    GanbyeongTheme {
        Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 일반 버튼
            GanbyeongButton(text = "신청하기", onClick = {})

            // 로딩 중 버튼
            GanbyeongButton(text = "신청하기", onClick = {}, isLoading = true)

            // 비활성화 버튼
            GanbyeongButton(text = "신청하기", onClick = {}, enabled = false)
        }
    }
}
