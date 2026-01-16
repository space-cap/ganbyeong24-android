package com.ezlevup.ganbyeong24.util

/** 전화번호 포맷팅 유틸리티 */
object PhoneNumberFormatter {

    /**
     * 전화번호를 010-1234-5678 형식으로 포맷팅합니다.
     *
     * @param input 입력된 전화번호 (숫자만 또는 하이픈 포함)
     * @return 포맷팅된 전화번호
     *
     * 예시:
     * - "01012345678" -> "010-1234-5678"
     * - "010-1234-5678" -> "010-1234-5678"
     * - "0101234" -> "010-1234"
     */
    fun format(input: String): String {
        // 숫자만 추출
        val digitsOnly = input.filter { it.isDigit() }

        // 길이에 따라 포맷팅
        return when {
            digitsOnly.length <= 3 -> digitsOnly
            digitsOnly.length <= 7 -> "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3)}"
            digitsOnly.length <= 11 -> {
                "${digitsOnly.substring(0, 3)}-${digitsOnly.substring(3, 7)}-${digitsOnly.substring(7)}"
            }
            else -> {
                // 11자리 초과 시 11자리까지만 사용
                val trimmed = digitsOnly.substring(0, 11)
                "${trimmed.substring(0, 3)}-${trimmed.substring(3, 7)}-${trimmed.substring(7)}"
            }
        }
    }

    /**
     * 포맷팅된 전화번호에서 숫자만 추출합니다.
     *
     * @param formatted 포맷팅된 전화번호
     * @return 숫자만 포함된 문자열
     *
     * 예시:
     * - "010-1234-5678" -> "01012345678"
     */
    fun removeFormat(formatted: String): String {
        return formatted.filter { it.isDigit() }
    }

    /**
     * 전화번호가 유효한 형식인지 확인합니다.
     *
     * @param phoneNumber 전화번호
     * @return 유효 여부
     */
    fun isValid(phoneNumber: String): Boolean {
        val digitsOnly = removeFormat(phoneNumber)
        return digitsOnly.matches(Regex("^010\\d{8}$"))
    }
}
