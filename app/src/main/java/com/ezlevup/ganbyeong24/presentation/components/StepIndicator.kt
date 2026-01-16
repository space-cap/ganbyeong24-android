package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 단계별 진행 상황을 표시하는 인디케이터
 *
 * @param currentStep 현재 단계 (1부터 시작)
 * @param totalSteps 전체 단계 수
 * @param stepLabels 각 단계의 라벨 (선택사항)
 * @param modifier Modifier
 */
@Composable
fun StepIndicator(
        currentStep: Int,
        totalSteps: Int,
        stepLabels: List<String> = emptyList(),
        modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        // 단계 원형 인디케이터
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
        ) {
            for (step in 1..totalSteps) {
                // 단계 원
                Box(
                        modifier =
                                Modifier.size(48.dp)
                                        .clip(CircleShape)
                                        .background(
                                                if (step <= currentStep) {
                                                    MaterialTheme.colorScheme.primary
                                                } else {
                                                    MaterialTheme.colorScheme.surfaceVariant
                                                }
                                        ),
                        contentAlignment = Alignment.Center
                ) {
                    Text(
                            text = step.toString(),
                            color =
                                    if (step <= currentStep) {
                                        MaterialTheme.colorScheme.onPrimary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                    )
                }

                // 연결선 (마지막 단계 제외)
                if (step < totalSteps) {
                    Box(
                            modifier =
                                    Modifier.weight(1f)
                                            .height(4.dp)
                                            .background(
                                                    if (step < currentStep) {
                                                        MaterialTheme.colorScheme.primary
                                                    } else {
                                                        MaterialTheme.colorScheme.surfaceVariant
                                                    }
                                            )
                    )
                }
            }
        }

        // 단계 라벨 (제공된 경우)
        if (stepLabels.isNotEmpty() && stepLabels.size == totalSteps) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                stepLabels.forEachIndexed { index, label ->
                    Text(
                            text = label,
                            fontSize = 14.sp,
                            color =
                                    if (index + 1 == currentStep) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                            fontWeight =
                                    if (index + 1 == currentStep) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    },
                            modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StepIndicatorPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            StepIndicator(
                    currentStep = 2,
                    totalSteps = 3,
                    stepLabels = listOf("환자 정보", "간병 기간", "연락처")
            )
        }
    }
}
