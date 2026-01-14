package com.ezlevup.ganbyeong24.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
        lightColorScheme(
                primary = Primary,
                onPrimary = Color.White,
                primaryContainer = PrimaryLight,
                onPrimaryContainer = PrimaryDark,
                secondary = Secondary,
                onSecondary = Color.White,
                error = Error,
                onError = Color.White,
                background = Background,
                onBackground = TextPrimary,
                surface = Surface,
                onSurface = TextPrimary,
                surfaceVariant = Surface,
                onSurfaceVariant = TextSecondary,
                outline = Divider
        )

@Composable
fun GanbyeongTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColorScheme, typography = Typography, content = content)
}
