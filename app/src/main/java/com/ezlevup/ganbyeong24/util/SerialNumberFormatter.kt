package com.ezlevup.ganbyeong24.util

/** 일련번호 포맷팅 유틸리티 */
object SerialNumberFormatter {

    /**
     * 일련번호를 포맷팅합니다.
     *
     * 예시:
     * - 10000000001 → "100-0000-0001"
     * - 20000000123 → "200-0000-0123"
     *
     * @param serialNumber 일련번호
     * @return 포맷팅된 문자열 (XXX-XXXX-XXXX)
     */
    fun format(serialNumber: Long): String {
        val str = serialNumber.toString()

        // 11자리 숫자인 경우 (100-0000-0000 형식)
        return if (str.length == 11) {
            "${str.substring(0, 3)}-${str.substring(3, 7)}-${str.substring(7)}"
        } else {
            // 예상치 못한 길이의 경우 그대로 반환
            str
        }
    }

    /**
     * 간병 신청 일련번호를 포맷팅합니다.
     *
     * @param serialNumber 일련번호
     * @return "간병 신청 #100-0000-0001"
     */
    fun formatCareRequest(serialNumber: Long): String {
        return "간병 신청 #${format(serialNumber)}"
    }

    /**
     * 간병사 일련번호를 포맷팅합니다.
     *
     * @param serialNumber 일련번호
     * @return "간병사 #200-0000-0001"
     */
    fun formatCaregiver(serialNumber: Long): String {
        return "간병사 #${format(serialNumber)}"
    }

    /**
     * 매칭 일련번호를 포맷팅합니다.
     *
     * @param serialNumber 일련번호
     * @return "매칭 #300-0000-0001"
     */
    fun formatMatch(serialNumber: Long): String {
        return "매칭 #${format(serialNumber)}"
    }
}
