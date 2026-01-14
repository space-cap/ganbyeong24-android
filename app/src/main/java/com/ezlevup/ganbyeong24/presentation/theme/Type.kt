package com.ezlevup.ganbyeong24.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography =
        Typography(
                // 화면 제목 (24sp, Bold)
                headlineLarge =
                        TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                lineHeight = 32.sp
                        ),

                // 섹션 제목 (20sp, Medium)
                headlineMedium =
                        TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                lineHeight = 28.sp
                        ),

                // 본문 텍스트 (16sp, Regular)
                bodyLarge =
                        TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                        ),

                // 버튼 텍스트 (18sp, Medium)
                labelLarge =
                        TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                                lineHeight = 24.sp
                        ),

                // 작은 텍스트 (14sp, Regular)
                bodySmall =
                        TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                        )
        )
