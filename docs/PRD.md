# 간병24 - 프로젝트 기획서 (PRD)

## 📋 프로젝트 개요

### 프로젝트 명
**간병24** - 간병인 매칭 플랫폼

### 프로젝트 목적
간병이 필요한 환자 및 보호자와 간병사를 연결하는 모바일 애플리케이션을 개발하여, 복잡한 절차 없이 간편하게 간병 서비스를 이용할 수 있도록 지원합니다.

### 핵심 가치 제안
- ✅ **간편한 사용**: 복잡한 회원가입 없이 필수 정보만 입력
- ✅ **시니어 친화적 UI**: 큰 폰트와 명확한 버튼으로 높은 시인성 제공
- ✅ **빠른 매칭**: 전화 상담을 통한 신속한 간병사 연결
- ✅ **단계적 입력**: 최소화된 입력 필드로 사용자 부담 감소

---

## 👥 타겟 사용자

### 주요 사용자 (보호자/환자)
- **연령대**: 40-70대
- **특성**:
  - 간병이 필요한 환자의 가족 또는 보호자
  - 모바일 앱 사용에 익숙하지 않을 수 있음
  - 빠르고 신뢰할 수 있는 간병사 매칭을 원함
  - 복잡한 절차를 선호하지 않음

### 간병사
- **연령대**: 40-60대
- **특성**:
  - 간병 경력 및 자격증 보유
  - 지역 기반으로 활동
  - 안정적인 일자리를 찾고 있음
  - 모바일 앱을 통한 간편한 등록 선호

---

## 🎯 주요 기능

### 1. 역할 선택
- 사용자가 "보호자" 또는 "간병사" 중 하나를 선택
- 선택에 따라 다른 입력 화면으로 이동

### 2. 간병 신청 (보호자용)
**필수 입력 정보**:
- 환자 상태 (예: 거동 가능 여부, 질환 종류)
- 간병 기간 (시작일, 종료일 또는 기간)
- 위치 (주소 또는 지역)
- 연락처 (전화번호)

**프로세스**:
1. 보호자가 필수 정보 입력
2. 신청 완료 화면 표시
3. 관리자가 전화로 상담 진행
4. 수동으로 적합한 간병사 매칭

### 3. 간병사 등록
**필수 입력 정보**:
- 경력 (년수 또는 상세 경력)
- 자격증 (보유 자격증 종류)
- 가능 지역 (활동 가능한 지역)
- 연락처 (전화번호)

**프로세스**:
1. 간병사가 필수 정보 입력
2. 등록 완료 화면 표시
3. 관리자가 검토 후 승인
4. 매칭 풀에 추가

### 4. 매칭 프로세스
**현재 버전 (v1.0)**:
- 수동 매칭 방식
- 관리자가 위치, 경력, 전문 분야를 고려하여 매칭
- 전화 상담을 통한 최종 확인

**향후 버전 (v2.0 이후)**:
- 자동 매칭 알고리즘 도입
- 앱 내 채팅/메시징 기능
- 실시간 매칭 상태 확인

---

## 📱 화면 구성

### 1. SplashScreen (인트로 화면)
**목적**: 앱 로딩 및 브랜드 인지
**구성 요소**:
- 간병24 로고
- 로딩 애니메이션
- 버전 정보 (하단)

**디자인 요구사항**:
- 파란색 계열의 브랜드 컬러 사용
- 깔끔하고 신뢰감 있는 디자인
- 2-3초 표시 후 자동 전환

---

### 2. RoleSelectionScreen (역할 선택 화면)
**목적**: 사용자 유형 구분
**구성 요소**:
- 제목: "간병24에 오신 것을 환영합니다"
- 설명: "원하시는 서비스를 선택해주세요"
- 버튼 1: "간병이 필요해요" (보호자용)
- 버튼 2: "간병사로 등록할게요" (간병사용)

**디자인 요구사항**:
- 버튼 크기: 최소 60dp 높이
- 폰트 크기: 제목 24sp, 버튼 텍스트 20sp
- 버튼 간격: 충분한 여백 (최소 16dp)
- 명확한 아이콘 사용 (보호자: 하트/케어 아이콘, 간병사: 사람 아이콘)

---

### 3. CareRequestScreen (간병 신청 화면)
**목적**: 보호자가 간병 서비스 신청
**구성 요소**:
- 제목: "간병 신청"
- 입력 필드:
  1. **환자 상태**
     - 타입: 드롭다운 또는 라디오 버튼
     - 옵션: "거동 가능", "거동 불가", "부분 도움 필요"
  2. **간병 기간**
     - 타입: 날짜 선택기 (시작일, 종료일)
     - 또는 기간 입력 (예: "1주일", "1개월")
  3. **위치**
     - 타입: 텍스트 입력 또는 주소 검색
     - 플레이스홀더: "예: 서울시 강남구"
  4. **연락처**
     - 타입: 전화번호 입력
     - 자동 포맷팅 (010-1234-5678)
- 버튼: "신청하기" (하단 고정)

**디자인 요구사항**:
- 입력 필드 높이: 최소 56dp
- 폰트 크기: 라벨 16sp, 입력 텍스트 18sp
- 필수 입력 표시: 빨간색 별표(*)
- 입력 필드 간격: 16dp
- 에러 메시지: 입력 필드 하단에 빨간색으로 표시

**유효성 검사**:
- 모든 필드 필수 입력
- 전화번호 형식 검증 (010으로 시작, 11자리)
- 간병 기간 유효성 검증 (시작일 < 종료일)

---

### 4. CaregiverRegistrationScreen (간병사 등록 화면)
**목적**: 간병사 정보 등록
**구성 요소**:
- 제목: "간병사 등록"
- 입력 필드:
  1. **경력**
     - 타입: 숫자 입력 또는 드롭다운
     - 옵션: "1년 미만", "1-3년", "3-5년", "5년 이상"
  2. **자격증**
     - 타입: 체크박스 또는 멀티 선택
     - 옵션: "요양보호사", "간호조무사", "간호사", "기타"
  3. **가능 지역**
     - 타입: 지역 선택 (드롭다운 또는 멀티 선택)
     - 예: "서울", "경기", "인천" 등
  4. **연락처**
     - 타입: 전화번호 입력
     - 자동 포맷팅
- 버튼: "등록하기" (하단 고정)

**디자인 요구사항**:
- CareRequestScreen과 동일한 디자인 시스템 적용
- 일관된 입력 필드 스타일
- 명확한 선택 옵션 제공

**유효성 검사**:
- 모든 필드 필수 입력
- 전화번호 형식 검증
- 최소 1개 이상의 자격증 선택
- 최소 1개 이상의 가능 지역 선택

---

### 5. ResultScreen (신청/등록 완료 화면)
**목적**: 신청 또는 등록 완료 확인
**구성 요소**:
- 성공 아이콘 (체크마크)
- 메시지:
  - 보호자: "간병 신청이 완료되었습니다"
  - 간병사: "간병사 등록이 완료되었습니다"
- 안내 문구:
  - "곧 담당자가 전화로 연락드릴 예정입니다"
  - "연락처: 1234-5678"
- 버튼:
  - "확인" (앱 종료 또는 홈으로 이동)

**디자인 요구사항**:
- 중앙 정렬
- 큰 성공 아이콘 (64dp 이상)
- 안심감을 주는 색상 (파란색 또는 초록색)
- 명확한 다음 단계 안내

---

## 🎨 UI/UX 디자인 가이드라인

### 색상 팔레트
**Primary Color (파란색 계열)**:
- Primary: `#2196F3` (밝은 파란색)
- Primary Dark: `#1976D2`
- Primary Light: `#BBDEFB`

**Secondary Color**:
- Secondary: `#4CAF50` (초록색 - 성공, 완료)
- Error: `#F44336` (빨간색 - 에러, 필수)

**Neutral Colors**:
- Background: `#FFFFFF`
- Surface: `#F5F5F5`
- Text Primary: `#212121`
- Text Secondary: `#757575`

### 타이포그래피
**폰트**: Noto Sans KR (한글 최적화)

**크기**:
- H1 (화면 제목): 24sp, Bold
- H2 (섹션 제목): 20sp, Medium
- Body (본문): 16sp, Regular
- Button: 18sp, Medium
- Caption (설명): 14sp, Regular

### 컴포넌트 스타일

#### 버튼
- **Primary Button**:
  - 높이: 56dp
  - 모서리: 8dp 둥근 모서리
  - 배경: Primary Color
  - 텍스트: White, 18sp, Medium
  - 그림자: 2dp elevation

- **Secondary Button**:
  - 높이: 56dp
  - 모서리: 8dp 둥근 모서리
  - 배경: Transparent
  - 테두리: 2dp, Primary Color
  - 텍스트: Primary Color, 18sp, Medium

#### 입력 필드
- **TextField**:
  - 높이: 56dp
  - 모서리: 4dp 둥근 모서리
  - 테두리: 1dp, #E0E0E0
  - Focus 시: 2dp, Primary Color
  - 패딩: 16dp (좌우), 12dp (상하)
  - 라벨: 16sp, Text Secondary
  - 입력 텍스트: 18sp, Text Primary

#### 간격 (Spacing)
- Extra Small: 4dp
- Small: 8dp
- Medium: 16dp
- Large: 24dp
- Extra Large: 32dp

### 접근성 (Accessibility)
- **터치 영역**: 최소 48dp x 48dp
- **색상 대비**: WCAG AA 기준 준수 (4.5:1 이상)
- **폰트 크기**: 최소 16sp (본문)
- **명확한 라벨**: 모든 입력 필드에 명확한 라벨 제공
- **에러 메시지**: 시각적 + 텍스트로 명확하게 표시

---

## 🛠 기술 스택

### 플랫폼
- **Android**: Kotlin, Jetpack Compose

### 아키텍처
- **패턴**: MVVM (Model-View-ViewModel)
- **의존성 주입**: Hilt (선택 사항)

### 주요 라이브러리
- **UI**: Jetpack Compose
- **Navigation**: Compose Navigation
- **백엔드**: Firebase
  - Firebase Firestore (데이터베이스)
  - Firebase Authentication (향후 로그인 기능용)
  - Firebase Cloud Messaging (향후 푸시 알림용)
- **비동기 처리**: Kotlin Coroutines, Flow
- **이미지**: Coil (향후 프로필 사진용)

### 개발 도구
- **IDE**: Android Studio
- **버전 관리**: Git
- **빌드 시스템**: Gradle (Kotlin DSL)

---

## 📊 데이터 모델

### Firestore 컬렉션 구조

#### 1. `care_requests` (간병 신청)
```kotlin
data class CareRequest(
    val id: String = "",                    // 문서 ID (자동 생성)
    val patientCondition: String = "",      // 환자 상태
    val careStartDate: Timestamp? = null,   // 간병 시작일
    val careEndDate: Timestamp? = null,     // 간병 종료일
    val location: String = "",              // 위치
    val phoneNumber: String = "",           // 연락처
    val status: String = "pending",         // 상태: pending, matched, completed
    val createdAt: Timestamp = Timestamp.now(),
    val matchedCaregiverId: String? = null  // 매칭된 간병사 ID (선택)
)
```

#### 2. `caregivers` (간병사)
```kotlin
data class Caregiver(
    val id: String = "",                    // 문서 ID (자동 생성)
    val experience: String = "",            // 경력
    val certificates: List<String> = emptyList(), // 자격증 목록
    val availableRegions: List<String> = emptyList(), // 가능 지역
    val phoneNumber: String = "",           // 연락처
    val status: String = "pending",         // 상태: pending, approved, active
    val createdAt: Timestamp = Timestamp.now(),
    val rating: Double? = null,             // 평점 (향후 기능)
    val completedCases: Int = 0             // 완료한 케이스 수 (향후 기능)
)
```

### 상태 관리 (State)
```kotlin
// 역할 선택 화면
enum class UserRole {
    GUARDIAN,    // 보호자
    CAREGIVER    // 간병사
}

// 신청/등록 상태
sealed class RequestState {
    object Idle : RequestState()
    object Loading : RequestState()
    data class Success(val message: String) : RequestState()
    data class Error(val message: String) : RequestState()
}
```

---

## 🔄 사용자 플로우

### 보호자 플로우
```
SplashScreen
    ↓
RoleSelectionScreen
    ↓ (간병이 필요해요 선택)
CareRequestScreen
    ↓ (정보 입력 및 신청)
ResultScreen (신청 완료)
    ↓
전화 상담 대기
```

### 간병사 플로우
```
SplashScreen
    ↓
RoleSelectionScreen
    ↓ (간병사로 등록할게요 선택)
CaregiverRegistrationScreen
    ↓ (정보 입력 및 등록)
ResultScreen (등록 완료)
    ↓
승인 대기
```

---

## 📈 향후 확장 계획

### Phase 2 (v2.0) - 자동화 및 커뮤니케이션
- [ ] 자동 매칭 알고리즘 구현
- [ ] 앱 내 채팅/메시징 기능
- [ ] 푸시 알림 (매칭 완료, 상담 요청 등)
- [ ] 매칭 상태 실시간 추적

### Phase 3 (v3.0) - 고급 기능
- [ ] 간병사 평가 및 리뷰 시스템
- [ ] 간병 일정 관리 캘린더
- [ ] 결제 시스템 통합
- [ ] 간병 일지 작성 기능

### Phase 4 (v4.0) - 플랫폼 확장
- [ ] iOS 앱 개발
- [ ] 웹 관리자 대시보드
- [ ] 데이터 분석 및 리포팅
- [ ] AI 기반 매칭 추천

---

## ✅ 성공 지표 (KPI)

### 사용자 지표
- 월간 활성 사용자 (MAU)
- 신규 가입자 수 (보호자 / 간병사)
- 앱 다운로드 수

### 비즈니스 지표
- 매칭 성공률
- 평균 매칭 소요 시간
- 사용자 만족도 (전화 상담 후 설문)

### 기술 지표
- 앱 크래시율 (< 1%)
- 평균 로딩 시간 (< 2초)
- API 응답 시간

---

## 📝 개발 우선순위

### P0 (필수 - v1.0)
- [x] SplashScreen
- [x] RoleSelectionScreen
- [x] CareRequestScreen
- [x] CaregiverRegistrationScreen
- [x] ResultScreen
- [x] Firebase Firestore 연동
- [x] 데이터 유효성 검사

### P1 (중요 - v1.1)
- [ ] 에러 핸들링 개선
- [ ] 로딩 상태 UI
- [ ] 입력 필드 자동 포맷팅
- [ ] 오프라인 모드 지원 (기본)

### P2 (향후 - v2.0+)
- [ ] 자동 매칭
- [ ] 채팅 기능
- [ ] 푸시 알림
- [ ] 관리자 대시보드

---

## 🔒 보안 및 개인정보 보호

### 데이터 보안
- Firebase Security Rules 설정
- 전화번호 암호화 저장 (선택)
- HTTPS 통신

### 개인정보 처리
- 개인정보 처리방침 작성 및 동의
- 최소한의 정보만 수집
- 데이터 보관 기간 설정

### 권한 관리
- 필요한 권한만 요청
- 권한 요청 시 명확한 설명 제공

---

## 📅 개발 일정 (예상)

### Week 1-2: 프로젝트 설정 및 기본 구조
- 프로젝트 생성 및 의존성 설정
- Firebase 프로젝트 생성 및 연동
- 디자인 시스템 구축 (Theme, Color, Typography)
- Navigation 구조 설정

### Week 3-4: 화면 개발
- SplashScreen 구현
- RoleSelectionScreen 구현
- CareRequestScreen 구현
- CaregiverRegistrationScreen 구현
- ResultScreen 구현

### Week 5: 백엔드 연동
- Firestore CRUD 작업
- ViewModel 및 Repository 구현
- 데이터 유효성 검사

### Week 6: 테스트 및 버그 수정
- 단위 테스트
- UI 테스트
- 실제 디바이스 테스트
- 버그 수정 및 최적화

### Week 7: 배포 준비
- 앱 아이콘 및 스플래시 이미지 최종화
- 스토어 등록 준비 (스크린샷, 설명)
- 베타 테스트

### Week 8: 출시
- Google Play Store 출시
- 모니터링 및 피드백 수집

---

## 📞 연락처 및 지원

### 개발팀 연락처
- 프로젝트 매니저: [이름]
- 개발자: [이름]
- 디자이너: [이름]

### 고객 지원
- 전화: 1234-5678
- 이메일: support@ganbyeong24.com
- 운영 시간: 평일 09:00 - 18:00

---

## 📚 참고 자료

### 디자인 참고
- Material Design 3 가이드라인
- 시니어 친화적 UI/UX 사례
- 헬스케어 앱 디자인 베스트 프랙티스

### 기술 문서
- [Jetpack Compose 공식 문서](https://developer.android.com/jetpack/compose)
- [Firebase Firestore 가이드](https://firebase.google.com/docs/firestore)
- [Android 접근성 가이드](https://developer.android.com/guide/topics/ui/accessibility)

---

## 버전 히스토리

| 버전 | 날짜 | 작성자 | 변경 사항 |
|------|------|--------|-----------|
| 1.0 | 2026-01-14 | Antigravity | 초기 PRD 작성 |

---

**문서 작성일**: 2026년 1월 14일  
**최종 수정일**: 2026년 1월 14일  
**문서 상태**: 초안 (Draft)
