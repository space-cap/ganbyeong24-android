package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 최근 신청한 환자명을 칩 버튼으로 표시하는 컴포넌트
 *
 * @param recentPatients 최근 환자명 목록
 * @param onPatientSelected 환자명 선택 콜백
 * @param onPatientRemoved 환자명 삭제 콜백
 * @param modifier Modifier
 */
@Composable
fun RecentPatientChips(
        recentPatients: List<String>,
        onPatientSelected: (String) -> Unit,
        onPatientRemoved: (String) -> Unit,
        modifier: Modifier = Modifier
) {
    if (recentPatients.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
                text = "최근 신청한 환자",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
        )

        LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(recentPatients) { patientName ->
                PatientChip(
                        patientName = patientName,
                        onSelected = { onPatientSelected(patientName) },
                        onRemoved = { onPatientRemoved(patientName) }
                )
            }
        }
    }
}

/** 개별 환자 칩 버튼 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PatientChip(
        patientName: String,
        onSelected: () -> Unit,
        onRemoved: () -> Unit,
        modifier: Modifier = Modifier
) {
    FilterChip(
            selected = false,
            onClick = onSelected,
            label = {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = patientName, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    IconButton(onClick = onRemoved, modifier = Modifier.size(24.dp)) {
                        Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "삭제",
                                modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            modifier = modifier.height(56.dp),
            colors =
                    FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
            border =
                    FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = false,
                            borderColor = MaterialTheme.colorScheme.outline,
                            borderWidth = 1.dp
                    )
    )
}

@Preview(showBackground = true)
@Composable
private fun RecentPatientChipsPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            RecentPatientChips(
                    recentPatients = listOf("홍길동", "김철수", "이영희"),
                    onPatientSelected = {},
                    onPatientRemoved = {}
            )
        }
    }
}
