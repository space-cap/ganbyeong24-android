package com.ezlevup.ganbyeong24.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ezlevup.ganbyeong24.presentation.theme.GanbyeongTheme

/**
 * 간병24 앱의 공통 입력 필드 컴포넌트
 *
 * 시니어 친화적인 큰 크기의 입력 필드로, 에러 메시지 표시를 지원합니다.
 *
 * @param value 현재 입력된 값
 * @param onValueChange 값이 변경될 때 호출되는 함수
 * @param label 입력 필드 라벨
 * @param modifier Modifier
 * @param placeholder 플레이스홀더 텍스트
 * @param isError 에러 상태 여부 (기본값: false)
 * @param errorMessage 에러 메시지 (isError가 true일 때 표시)
 * @param keyboardOptions 키보드 옵션
 * @param visualTransformation 입력 값 변환 (예: 비밀번호 마스킹)
 * @param singleLine 한 줄 입력 여부 (기본값: true)
 * @param maxLines 최대 줄 수 (기본값: 1)
 * @param textStyle 텍스트 스타일 (기본값: MaterialTheme.typography.bodyLarge)
 */
@Composable
fun GanbyeongTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        modifier: Modifier = Modifier,
        placeholder: String = "",
        isError: Boolean = false,
        errorMessage: String? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        singleLine: Boolean = true,
        maxLines: Int = 1,
        textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge
) {
        Column(modifier = modifier) {
                OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = { Text(text = label, style = MaterialTheme.typography.bodyLarge) },
                        placeholder = {
                                Text(text = placeholder, style = MaterialTheme.typography.bodyLarge)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = isError,
                        keyboardOptions = keyboardOptions,
                        visualTransformation = visualTransformation,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        textStyle = textStyle,
                        shape = RoundedCornerShape(8.dp),
                        colors =
                                OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                        errorBorderColor = MaterialTheme.colorScheme.error
                                )
                )

                // 에러 메시지 표시
                if (isError && errorMessage != null) {
                        Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                }
        }
}

@Preview(showBackground = true)
@Composable
private fun GanbyeongTextFieldPreview() {
        GanbyeongTheme {
                Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                        // 일반 입력 필드
                        GanbyeongTextField(
                                value = "",
                                onValueChange = {},
                                label = "환자명 *",
                                placeholder = "예: 홍길동"
                        )

                        // 입력된 상태
                        GanbyeongTextField(
                                value = "홍길동",
                                onValueChange = {},
                                label = "환자명 *",
                                placeholder = "예: 홍길동"
                        )

                        // 에러 상태
                        GanbyeongTextField(
                                value = "홍",
                                onValueChange = {},
                                label = "환자명 *",
                                placeholder = "예: 홍길동",
                                isError = true,
                                errorMessage = "환자명은 2자 이상 입력해주세요"
                        )

                        // 여러 줄 입력
                        GanbyeongTextField(
                                value = "당뇨병 환자입니다.\n혈압 관리가 필요합니다.",
                                onValueChange = {},
                                label = "환자 상태",
                                placeholder = "환자의 상태를 입력해주세요",
                                singleLine = false,
                                maxLines = 4
                        )
                }
        }
}
