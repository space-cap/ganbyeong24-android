package com.ezlevup.ganbyeong24.presentation.navigation

/**
 * 앱의 모든 화면 경로를 정의하는 sealed class
 *
 * Navigation Compose에서 사용할 화면 경로를 관리합니다.
 */
sealed class Screen(val route: String) {
    /** 스플래시 화면 앱 시작 시 2초간 표시되는 인트로 화면 */
    object Splash : Screen("splash")

    /** 역할 선택 화면 사용자가 보호자 또는 간병사 역할을 선택하는 화면 */
    object RoleSelection : Screen("role_selection")

    /** 간병 신청 화면 보호자가 간병 서비스를 신청하는 화면 */
    object CareRequest : Screen("care_request")

    /** 간병사 등록 화면 간병사가 자신의 정보를 등록하는 화면 */
    object CaregiverRegistration : Screen("caregiver_registration")

    /**
     * 결과 화면 신청 또는 등록 완료 후 표시되는 화면
     *
     * @param userRole 사용자 역할 (guardian 또는 caregiver)
     */
    object Result : Screen("result/{userRole}") {
        /**
         * userRole 파라미터를 포함한 경로 생성
         *
         * @param userRole 사용자 역할 ("guardian" 또는 "caregiver")
         * @return 완성된 경로 문자열
         */
        fun createRoute(userRole: String) = "result/$userRole"
    }
}
