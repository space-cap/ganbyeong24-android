package com.ezlevup.ganbyeong24.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * 전화번호를 010-1234-5678 형식으로 표시하는 VisualTransformation
 *
 * 실제 값은 숫자만 저장하고, 화면에만 하이픈을 추가하여 표시합니다. 이렇게 하면 커서 위치가 정확하게 유지됩니다.
 */
class PhoneNumberVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digitsOnly = text.text.filter { it.isDigit() }

        val formatted =
                when {
                    digitsOnly.length <= 3 -> digitsOnly
                    digitsOnly.length <= 7 ->
                            "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3)}"
                    digitsOnly.length <= 11 -> {
                        "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3, 7)}-${digitsOnly.substring(7)}"
                    }
                    else -> {
                        val trimmed = digitsOnly.substring(0, 11)
                        "${trimmed.substring(0, 3)}-${trimmed.substring(3, 7)}-${trimmed.substring(7)}"
                    }
                }

        return TransformedText(
                text = AnnotatedString(formatted),
                offsetMapping = PhoneNumberOffsetMapping(digitsOnly, formatted)
        )
    }
}

/** 원본 텍스트와 변환된 텍스트 간의 커서 위치 매핑 */
private class PhoneNumberOffsetMapping(
        private val original: String,
        private val formatted: String
) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 0) return 0
        if (offset >= original.length) return formatted.length

        var transformedOffset = 0
        var originalCount = 0

        for (i in formatted.indices) {
            if (formatted[i] == '-') {
                transformedOffset++
            } else {
                if (originalCount >= offset) break
                originalCount++
                transformedOffset++
            }
        }

        return transformedOffset
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 0) return 0
        if (offset >= formatted.length) return original.length

        var originalOffset = 0

        for (i in 0 until offset) {
            if (formatted[i] != '-') {
                originalOffset++
            }
        }

        return originalOffset.coerceAtMost(original.length)
    }
}
