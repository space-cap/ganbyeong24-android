package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 환자 상태를 선택하는 버튼 그룹
 *
 * 시니어 사용자가 쉽게 선택할 수 있도록 큰 버튼으로 구성
 *
 * @param selectedCondition 현재 선택된 상태
 * @param onConditionSelected 상태 선택 콜백
 * @param modifier Modifier
 */
@Composable
fun PatientConditionSelector(
        selectedCondition: String,
        onConditionSelected: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    val conditions =
            listOf(
                    "거동 가능" to "스스로 움직일 수 있어요",
                    "거동 불가" to "움직임이 어려워요",
                    "휠체어 사용" to "휠체어가 필요해요",
                    "와상 환자" to "누워서 생활해요"
            )

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
                text = "환자 상태 *",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        conditions.forEach { (condition, description) ->
            ConditionButton(
                    condition = condition,
                    description = description,
                    isSelected = selectedCondition == condition,
                    onClick = { onConditionSelected(condition) }
            )
        }
    }
}

@Composable
private fun ConditionButton(
        condition: String,
        description: String,
        isSelected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    OutlinedCard(
            onClick = onClick,
            modifier = modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(12.dp),
            colors =
                    CardDefaults.outlinedCardColors(
                            containerColor =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                    ),
            border =
                    BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color =
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.outline
                                    }
                    )
    ) {
        Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center
        ) {
            Text(
                    text = condition,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color =
                            if (isSelected) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = description,
                    fontSize = 14.sp,
                    color =
                            if (isSelected) {
                                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PatientConditionSelectorPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            PatientConditionSelector(selectedCondition = "거동 불가", onConditionSelected = {})
        }
    }
}
