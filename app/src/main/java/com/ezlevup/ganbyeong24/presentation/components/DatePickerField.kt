package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme
import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜를 선택할 수 있는 필드
 *
 * DatePicker를 사용하여 시니어 사용자가 쉽게 날짜를 선택할 수 있도록 함
 *
 * @param label 필드 라벨
 * @param selectedDate 선택된 날짜 (yyyy-MM-dd 형식)
 * @param onDateSelected 날짜 선택 콜백
 * @param isError 에러 상태
 * @param errorMessage 에러 메시지
 * @param modifier Modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
        label: String,
        selectedDate: String,
        onDateSelected: (String) -> Unit,
        isError: Boolean = false,
        errorMessage: String? = null,
        modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.KOREA) }
    val displayFormatter = remember { SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
                value =
                        if (selectedDate.isNotEmpty()) {
                            try {
                                val date = dateFormatter.parse(selectedDate)
                                displayFormatter.format(date ?: Date())
                            } catch (e: Exception) {
                                selectedDate
                            }
                        } else {
                            ""
                        },
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("날짜를 선택하세요", fontSize = 18.sp) },
                trailingIcon = {
                    Icon(
                            Icons.Default.DateRange,
                            contentDescription = "날짜 선택",
                            modifier = Modifier.size(28.dp)
                    )
                },
                isError = isError,
                supportingText =
                        if (isError && errorMessage != null) {
                            {
                                Text(
                                        text = errorMessage,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = androidx.compose.ui.graphics.Color(0xFFD32F2F)
                                )
                            }
                        } else null,
                modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                colors =
                        OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = MaterialTheme.colorScheme.outline,
                                disabledPlaceholderColor =
                                        MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledTrailingIconColor =
                                        MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                enabled = false
        )
    }

    if (showDatePicker) {
        val datePickerState =
                rememberDatePickerState(
                        initialSelectedDateMillis =
                                if (selectedDate.isNotEmpty()) {
                                    try {
                                        dateFormatter.parse(selectedDate)?.time
                                    } catch (e: Exception) {
                                        null
                                    }
                                } else {
                                    null
                                }
                )

        DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val date = Date(millis)
                                    onDateSelected(dateFormatter.format(date))
                                }
                                showDatePicker = false
                            }
                    ) { Text("확인", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("취소", fontSize = 18.sp)
                    }
                }
        ) {
            DatePicker(
                    state = datePickerState,
                    title = {
                        Text(
                                text = label,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerFieldPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            DatePickerField(label = "간병 시작일 *", selectedDate = "2024-01-15", onDateSelected = {})
        }
    }
}
