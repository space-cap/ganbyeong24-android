package com.ezlevup.ganbyeong24.presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import kotlinx.coroutines.delay

/**
 * 스플래시 화면
 *
 * 앱 시작 시 표시되는 인트로 화면입니다.
 * - 간병24 로고 및 앱 이름 표시
 * - 페이드인 애니메이션 효과
 * - 2초 후 자동으로 역할 선택 화면으로 이동
 *
 * @param onNavigateToRoleSelection 역할 선택 화면으로 이동하는 콜백
 */
@Composable
fun SplashScreen(onNavigateToRoleSelection: () -> Unit) {
    // 페이드인 애니메이션을 위한 alpha 값
    val alpha = remember { Animatable(0f) }

    // 화면 진입 시 애니메이션 실행 및 자동 이동
    LaunchedEffect(Unit) {
        // 페이드인 애니메이션 (0.8초)
        alpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 800))

        // 2초 대기 후 다음 화면으로 이동
        delay(2000)
        onNavigateToRoleSelection()
    }

    Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.alpha(alpha.value)
        ) {
            // 로고 아이콘 (하트 모양)
            Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "간병24 로고",
                    tint = Color.White,
                    modifier = Modifier.size(120.dp)
            )

            // 앱 이름
            Text(text = "간병24", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)

            // 로딩 인디케이터
            CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 3.dp
            )
        }

        // 버전 정보
        Text(
                text = "v1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f),
                modifier =
                        Modifier.align(Alignment.BottomCenter)
                                .padding(bottom = 32.dp)
                                .alpha(alpha.value)
        )
    }
}

/** 스플래시 화면 프리뷰 */
@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    GanbyeongTheme { SplashScreen(onNavigateToRoleSelection = {}) }
}
