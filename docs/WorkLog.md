# 간병24 개발 일지

## 📋 프로젝트 정보
- **프로젝트명**: 간병24
- **패키지명**: com.ezlevup.ganbyeong24
- **시작일**: 2026-01-14
- **개발자**: space-cap

---

## 📅 2026-01-14 (Day 1)

### ✅ 완료한 작업

#### 1단계: 프로젝트 초기 설정
- [x] **Issue #1**: Firebase 프로젝트 생성 및 Android 앱 연동
  - Firebase Console에서 'Ganbyeong24' 프로젝트 생성
  - Firestore Database 활성화 (서울 리전)
  - google-services.json 추가
  - .gitignore 설정

- [x] **Issue #2**: 프로젝트 의존성 추가
  - Navigation Compose 2.8.5
  - Koin 4.0.1 (의존성 주입)
  - ViewModel Compose 2.10.0
  - Coroutines 1.9.0
  - libs.versions.toml에 버전 관리

- [x] **Issue #3**: 패키지 구조 생성
  - MVVM 아키텍처 패키지 구조 생성
  - di, data, presentation, util 패키지
  - 5개 화면별 패키지 (splash, role, care_request, caregiver, result)

#### 2단계: 기반 구축
- [x] **Issue #4**: 테마 시스템 구축
  - Color.kt: 파란색 계열 Primary 색상 (#2196F3)
  - Type.kt: 시니어 친화적 큰 폰트 (16sp~24sp)
  - Theme.kt: Material3 GanbyeongTheme
  - MainActivity에 테마 적용 및 테스트

- [x] **Issue #5**: 공통 컴포넌트 개발
  - GanbyeongButton.kt: 높이 56dp, 로딩 상태 지원
  - GanbyeongTextField.kt: 에러 메시지 표시 지원
  - Preview 추가 (다양한 상태 확인)

- [x] **Issue #6**: Navigation 구조 설정
  - Screen.kt: 5개 화면 경로 정의
  - NavGraph.kt: Navigation 구조 및 임시 화면
  - Splash → RoleSelection → CareRequest/CaregiverRegistration → Result 플로우

- [x] **Issue #13**: Koin 의존성 주입 설정
  - AppModule.kt: Firebase Firestore 등록
  - GanbyeongApplication.kt: Koin 초기화
  - AndroidManifest.xml: Application 클래스 지정
  - Logcat에서 Koin 초기화 확인

#### 문서 작성
- [x] PRD.md (프로젝트 기획서)
- [x] TechnicalDesign.md (기술 설계서)
- [x] ScreenDesign.md (화면 설계서)
- [x] DevelopmentGuide.md (개발 가이드)
- [x] DevelopmentRoadmap.md (개발 로드맵)
- [x] WorkLog.md (작업 일지)

### 📝 배운 것
- Git 브랜치 전략 (feature → develop → main)
- GitHub PR 머지 후 로컬 동기화 (`git pull origin develop`)
- .gitignore로 민감한 파일 제외
- Material3 테마 시스템
- Compose Navigation 구조
- Koin 의존성 주입 설정
- GitHub CLI로 이슈 등록 (`gh issue create`)

### ⚠️ 이슈 및 해결
- **문제**: google-services.json이 Git에 추가됨
  - **해결**: `git rm --cached` 및 .gitignore 설정
  
- **문제**: main 브랜치에 직접 머지
  - **해결**: develop을 main과 동기화 (`git merge main`)
  
- **문제**: GitHub PR 머지 후 로컬에 반영 안 됨
  - **해결**: `git pull origin develop`로 최신 상태 가져오기

- **문제**: GitHub 이슈 수동 등록이 번거로움
  - **해결**: GitHub CLI (`gh issue create`) 사용

---

## 📅 2026-01-15 (Day 2)

### ✅ 완료한 작업

#### 3단계: 화면 개발 (진행 중)

- [x] **Issue #15**: SplashScreen 개발 - 앱 시작 화면 구현
  - GitHub 이슈 등록 (#15)
  - feature/splash-screen 브랜치 생성
  - SplashScreen.kt 구현
    - 배경 이미지 적용 (ic_background.jpg)
    - "간병24" 텍스트 표시 (32sp, Bold, 흰색)
    - 페이드인 애니메이션 효과 (0.8초)
    - LaunchedEffect로 2초 딜레이 후 자동 이동
    - 로딩 인디케이터 (CircularProgressIndicator)
    - 버전 정보 표시 (v1.0.0)
  - NavGraph.kt 업데이트
    - SplashScreen import 추가
    - SplashScreenPlaceholder를 실제 SplashScreen으로 교체
  - 빌드 및 실행 테스트 성공

### 📝 배운 것
- AI 이미지 생성 및 Android 리소스 추가
- Compose 애니메이션 (Animatable, tween)
- Image Composable과 ContentScale 사용
- 투명 배경 이미지 처리
- 배경 이미지 위에 콘텐츠 레이어링

### ⚠️ 이슈 및 해결
- **문제**: AI 생성 로고 이미지에 투명 배경이 포함되어 격자 무늬 표시
  - **시도**: 흰색 배경 로고 생성 → 배경 이미지와 어울리지 않음
  - **시도**: 투명 배경 로고 재생성 → 여전히 격자 무늬 표시
  - **해결**: 로고 이미지를 주석 처리하고 나중에 추가하기로 결정
  - **교훈**: 프리뷰와 실제 실행 환경의 차이 이해 필요

### ✅ 완료한 작업 (추가)

#### SplashScreen 마무리
- [x] **Issue #15 완료**: SplashScreen 개발 완료
  - Git commit & push 완료
  - PR 생성 및 머지 완료 (`feature/splash-screen` → `develop`)
  - 브랜치 정리 완료
  - 빌드 및 실행 테스트 성공

#### RoleSelectionScreen 개발
- [x] **Issue #17**: RoleSelectionScreen 구현 - 역할 선택 화면
  - GitHub 이슈 등록 (#17)
  - feature/role-selection 브랜치 생성
  - RoleSelectionScreen.kt 구현
    - 제목: "간병24에 오신 것을 환영합니다"
    - 설명: "원하시는 서비스를 선택해주세요"
    - "간병이 필요해요" 버튼 (하트 아이콘)
    - "간병사로 등록할게요" 버튼 (사람 아이콘)
    - 시니어 친화적 큰 버튼 (높이 80dp)
  - NavGraph.kt 업데이트
    - RoleSelectionScreen import 추가
    - RoleSelectionScreenPlaceholder를 실제 RoleSelectionScreen으로 교체
  - 빌드 및 실행 테스트 성공
  - Git commit & push 완료
  - PR 생성 및 머지 완료 (`feature/role-selection` → `develop`)

### 📝 배운 것 (추가)
- Material Icons 사용 (Favorite, Person)
- Compose Button 커스터마이징 (높이, 모양, 색상)
- 네비게이션 콜백 파라미터 명명 규칙
- ScreenDesign.md 명세를 따른 구현
- Firestore 데이터 저장 및 조회
- Repository 패턴 구현
- ViewModel 유효성 검사 로직
- Koin 의존성 주입 (Repository, ViewModel)

#### CareRequestScreen 개발
- [x] **Issue #19**: CareRequestScreen 구현 - 간병 신청 화면
  - GitHub 이슈 등록 (#19)
  - feature/care-request 브랜치 생성
  - 데이터 레이어 구현
    - CareRequest 데이터 모델 생성
    - CareRequestRepository 인터페이스 및 구현체
    - Firestore 연동 (care_requests 컬렉션)
  - Presentation 레이어 구현
    - CareRequestState 데이터 클래스
    - CareRequestViewModel (StateFlow, 유효성 검사)
  - UI 레이어 구현
    - CareRequestScreen.kt (8개 입력 필드)
    - TopAppBar 및 뒤로가기 기능
    - 유효성 검사 에러 메시지 표시
    - 로딩 상태 처리
    - 에러 다이얼로그
  - Koin 모듈 업데이트
    - CareRequestRepository 등록
    - CareRequestViewModel 등록
  - NavGraph.kt 업데이트
    - CareRequestScreenPlaceholder → 실제 화면 교체
  - 빌드 및 실행 테스트 성공
  - Firestore 저장 테스트 성공
  - Git commit & push 완료
  - PR 생성 및 머지 완료 (`feature/care-request` → `develop`)

#### CaregiverRegistrationScreen 개발
- [x] **Issue #21**: CaregiverRegistrationScreen 구현 - 간병사 등록 화면
  - GitHub 이슈 등록 (#21)
  - feature/caregiver-registration 브랜치 생성
  - 데이터 레이어 구현
    - Caregiver 데이터 모델 생성
    - CaregiverRepository 인터페이스 및 구현체
    - Firestore 연동 (caregivers 컬렉션)
  - Presentation 레이어 구현
    - CaregiverRegistrationState 데이터 클래스
    - CaregiverRegistrationViewModel (StateFlow, 유효성 검사)
  - UI 레이어 구현
    - CaregiverRegistrationScreen.kt (5개 입력 필드)
    - TopAppBar 및 뒤로가기 기능
    - 유효성 검사 에러 메시지 표시
    - 로딩 상태 처리
    - 에러 다이얼로그
  - Koin 모듈 업데이트
    - CaregiverRepository 등록
    - CaregiverRegistrationViewModel 등록
  - NavGraph.kt 업데이트
    - CaregiverRegistrationScreenPlaceholder → 실제 화면 교체
  - 빌드 및 실행 테스트 성공
  - Firestore 저장 테스트 성공
  - Git commit & push 완료
  - PR 생성 및 머지 완료 (`feature/caregiver-registration` → `develop`)

#### ResultScreen 개발
- [x] **Issue #23**: ResultScreen 구현 - 완료 화면
  - GitHub 이슈 등록 (#23)
  - feature/result-screen 브랜치 생성
  - UI 레이어 구현
    - ResultScreen.kt 생성
    - 성공 아이콘 (CheckCircle, 80dp)
    - 역할별 메시지 분기 (guardian/caregiver)
    - 안내 문구 표시
    - 확인 버튼
  - NavGraph.kt 업데이트
    - ResultScreenPlaceholder → 실제 화면 교체
  - 빌드 및 실행 테스트 성공
    - Guardian 역할 테스트 성공
    - Caregiver 역할 테스트 성공
  - Git commit & push 완료
  - PR 생성 및 머지 완료 (`feature/result-screen` → `develop`)

### 🎉 3단계: 화면 개발 완료!

**완료된 화면 (5/5):**
1. ✅ SplashScreen
2. ✅ RoleSelectionScreen
3. ✅ CareRequestScreen
4. ✅ CaregiverRegistrationScreen
5. ✅ ResultScreen

---

## 🎯 다음 작업

### 4단계: 데이터 레이어 개선 (추후)
- [ ] 실시간 데이터 조회 기능
- [ ] 데이터 캐싱
- [ ] 오프라인 지원

### 5단계: 테스트 및 배포 (추후)
- [ ] Unit 테스트 작성
- [ ] UI 테스트 작성
- [ ] 배포 준비

---

## 📊 진행 상황

### 전체 로드맵 (5단계)
- ✅ 1단계: 프로젝트 초기 설정 (100%)
- ✅ 2단계: 기반 구축 (100%)
- ✅ 3단계: 화면 개발 (100% - 5/5 화면 완료) 🎉
- ⏳ 4단계: 데이터 레이어 (0%)
- ⏳ 5단계: 테스트 및 배포 (0%)

### GitHub Issues
- ✅ Closed: #1, #2, #3, #4, #5, #6, #13, #15, #17, #19, #21, #23
- 🎉 3단계 화면 개발 완료!

---

## 💡 메모

### 프로젝트 구조
```
com.ezlevup.ganbyeong24/
├── di/                          # ✅ Koin 모듈 (Repository, ViewModel)
├── data/
│   ├── model/                   # ✅ CareRequest, Caregiver
│   └── repository/              # ✅ CareRequestRepository, CaregiverRepository, AuthRepository
├── presentation/
│   ├── theme/                   # ✅ Color, Type, Theme
│   ├── components/              # ✅ Button, TextField
│   ├── navigation/              # ✅ Screen, NavGraph
│   └── screens/
│       ├── auth/                # ✅ LoginScreen, SignupScreen (완료)
│       ├── splash/              # ✅ SplashScreen (완료)
│       ├── role/                # ✅ RoleSelectionScreen (완료)
│       ├── care_request/        # ✅ CareRequestScreen (완료)
│       ├── caregiver/           # ✅ CaregiverRegistrationScreen (완료)
│       └── result/              # ✅ ResultScreen (완료)
└── util/
```

## 📊 4단계: Firebase Authentication 구현 (2026-01-15)

### ✅ 완료 내용

#### Firebase 설정
- Firebase Console에서 Authentication 활성화 (이메일/비밀번호)
- `build.gradle.kts`에 `firebase-auth-ktx` 의존성 추가

#### 데이터 레이어
- **CareRequest.kt**: `userId` 필드 추가
- **Caregiver.kt**: `userId` 필드 추가
- **AuthRepository.kt**: 인증 Repository 인터페이스
- **AuthRepositoryImpl.kt**: Firebase Authentication 구현
  - `login()`: 이메일/비밀번호 로그인
  - `signup()`: 회원가입
  - `logout()`: 로그아웃
  - `getCurrentUserId()`: 현재 사용자 ID 조회
  - `isLoggedIn()`: 로그인 상태 확인

#### Presentation 레이어
- **LoginState.kt**: 로그인 화면 상태
- **LoginViewModel.kt**: 로그인 비즈니스 로직
  - 이메일/비밀번호 유효성 검사
  - 로그인 처리
- **SignupState.kt**: 회원가입 화면 상태
- **SignupViewModel.kt**: 회원가입 비즈니스 로직
  - 이메일/비밀번호/비밀번호확인 유효성 검사
  - 회원가입 처리

#### UI 레이어
- **LoginScreen.kt**: 로그인 화면
  - 이메일/비밀번호 입력
  - 로그인 버튼
  - 회원가입 링크
  - 에러 다이얼로그
- **SignupScreen.kt**: 회원가입 화면
  - 이메일/비밀번호/비밀번호확인 입력
  - 회원가입 버튼
  - TopAppBar with 뒤로가기
  - 에러 다이얼로그

#### Navigation
- **Screen.kt**: Login, Signup 화면 추가
- **NavGraph.kt**: 인증 흐름 통합
  - SplashScreen에서 로그인 상태 확인
  - 로그인 안 됨 → LoginScreen
  - 로그인 됨 → RoleSelectionScreen
  - LoginScreen → SignupScreen
  - 로그인/회원가입 성공 → RoleSelectionScreen

#### Koin DI
- **AppModule.kt** 업데이트
  - `FirebaseAuth` 인스턴스 등록
  - `AuthRepository` 등록
  - `LoginViewModel`, `SignupViewModel` 등록
  - `CareRequestViewModel`, `CaregiverRegistrationViewModel`에 `AuthRepository` 주입

#### 기존 코드 수정
- **CareRequestViewModel.kt**: `userId` 자동 추가
- **CaregiverRegistrationViewModel.kt**: `userId` 자동 추가

#### 테스트
- ✅ 빌드 성공
- ✅ 회원가입 테스트 성공
- ✅ 로그인 테스트 성공
- ✅ 데이터 저장 시 userId 확인 (Firestore)
- ✅ 로그인 상태 유지 확인

#### GitHub
- **Issue #26**: Firebase Authentication 구현
- **PR**: feature/firebase-auth → develop (머지 완료)

### 📝 작업 체크리스트

#### 0단계: 준비
- [x] GitHub 이슈 등록 (#26)
- [x] feature/firebase-auth 브랜치 생성

#### 1단계: Firebase 설정
- [x] Firebase Console에서 Authentication 활성화
- [x] build.gradle.kts에 firebase-auth 의존성 추가
- [x] 빌드 확인

#### 2단계: 데이터 모델 업데이트
- [x] CareRequest에 userId 필드 추가
- [x] Caregiver에 userId 필드 추가

#### 3단계: Repository 구현
- [x] AuthRepository 인터페이스 생성
- [x] AuthRepositoryImpl 구현
- [x] 로그인, 회원가입, 로그아웃, 현재 사용자 확인

#### 4단계: ViewModel 구현
- [x] LoginState, LoginViewModel
- [x] SignupState, SignupViewModel

#### 5단계: UI 구현
- [x] LoginScreen.kt 생성
- [x] SignupScreen.kt 생성
- [x] Navigation 업데이트

#### 6단계: 기존 코드 수정
- [x] CareRequestViewModel에서 userId 자동 추가
- [x] CaregiverRegistrationViewModel에서 userId 자동 추가
- [x] Koin 모듈 업데이트

#### 7단계: 테스트
- [x] 빌드 테스트
- [x] 회원가입 테스트
- [x] 로그인 테스트
- [x] 데이터 저장 시 userId 확인

#### 8단계: 마무리
- [x] Git commit & push
- [x] PR 생성 및 머지
- [x] 문서 업데이트

---

## 📂 프로젝트 구조 (최종)

```
app/src/main/java/com/ezlevup/ganbyeong24/
├── data/
│   ├── model/
│   │   ├── CareRequest.kt       # ✅ userId 추가
│   │   └── Caregiver.kt         # ✅ userId 추가
│   └── repository/
│       ├── AuthRepository.kt              # ✅ 인증 인터페이스
│       ├── AuthRepositoryImpl.kt          # ✅ Firebase Auth 구현
│       ├── CareRequestRepository.kt       # ✅
│       ├── CareRequestRepositoryImpl.kt   # ✅
│       ├── CaregiverRepository.kt         # ✅
│       └── CaregiverRepositoryImpl.kt     # ✅
├── presentation/
│   ├── theme/                   # ✅ Color, Type, Theme
│   ├── components/              # ✅ Button, TextField
│   ├── navigation/              # ✅ Screen, NavGraph
│   └── screens/
│       ├── auth/                # ✅ 인증 화면
│       │   ├── LoginScreen.kt
│       │   ├── LoginState.kt
│       │   ├── LoginViewModel.kt
│       │   ├── SignupScreen.kt
│       │   ├── SignupState.kt
│       │   └── SignupViewModel.kt
│       ├── splash/              # ✅ SplashScreen
│       ├── role/                # ✅ RoleSelectionScreen
│       ├── care_request/        # ✅ CareRequestScreen
│       ├── caregiver/           # ✅ CaregiverRegistrationScreen
│       └── result/              # ✅ ResultScreen
└── di/
    └── AppModule.kt             # ✅ Koin DI 설정
```

### 중요 명령어
```bash
# 작업 시작
git checkout develop
git pull origin develop
git checkout -b feature/작업명

# 작업 완료
git add .
git commit -m "커밋 메시지"
git push origin feature/작업명

# GitHub PR 머지 후
git checkout develop
git pull origin develop

# GitHub 이슈 등록
gh issue create --title "제목" --body-file issue.md
```

### SplashScreen Git 커밋 메시지 (참고)
```bash
git commit -m "feat: SplashScreen 구현 (#15)

- 배경 이미지 적용 (ic_background.jpg)
- 페이드인 애니메이션 효과 구현 (0.8초)
- 2초 후 자동으로 역할 선택 화면으로 이동
- NavGraph에 실제 SplashScreen 연동
- 로딩 인디케이터 및 버전 정보 표시
- 로고 이미지는 나중에 추가 예정 (TODO)"
```

### 참고 문서
- [DevelopmentRoadmap.md](./DevelopmentRoadmap.md)
- [TechnicalDesign.md](./TechnicalDesign.md)
- [ScreenDesign.md](./ScreenDesign.md)

---

## 🔖 북마크

### 다음 대화 시작 시 말할 것
```
"간병24 프로젝트 개발 중이야.
docs/WorkLog.md 파일 확인해줘.
다음 작업 시작하자!"
```

---

**마지막 업데이트**: 2026-01-16 20:31

---

## � 2026-01-16 (Day 3)

### ✅ 완료한 작업

#### 4단계: 시니어 친화적 UI 개선 (Issue #28)
- [x] **CareRequestScreen 리팩토링**:
  - 화면을 3단계로 분리 (환자 정보 → 간병 기간 → 연락처)
  - `StepIndicator`, `PatientConditionSelector` 등 공통 컴포넌트 개발
  - `LocationSelector`로 위치 입력 개선 (시/도, 구/군 드롭다운)
- [x] **입력 편의성 강화**:
  - `DatePickerField`: 날짜 선택 UI 적용
  - `PhoneNumberVisualTransformation`: 전화번호 자동 포맷팅 및 커서 제어
  - 날짜 유효성 검사 (시작일 < 종료일 등)
  - 에러 메시지 시인성 개선 (크고 굵은 빨간색 텍스트)

#### 5단계: 최근 신청 환자 빠른 선택 (Issue #30)
- [x] **Room Database 구축**:
  - `RecentPatient` Entity, Access Object (DAO) 생성
  - `AppDatabase` 설정 및 Koin 주입
  - `RecentPatientRepository`: 최근 환자 5명 자동 관리 로직
- [x] **UI 통합**:
  - `RecentPatientChips`: 칩 버튼 형태의 빠른 선택 UI
  - 간병 신청 성공 시 자동 저장
  - 목록에서 삭제 기능 구현

### 📝 배운 것
- **VisualTransformation**: 원본 데이터(숫자)와 표시 데이터(형식화된 번호)를 분리하여 커서 튐 현상 해결
- **Room + KSP**: Kotlin 2.0.21과 KSP 버전 간의 호환성 이슈 해결 (`2.0.21-1.0.28`)
- **Jetpack Compose Preview**: 컴포넌트 파라미터 변경 시 Preview도 함께 업데이트해야 함
- **Material3 FilterChip**: `enabled`와 `selected` 속성의 필수값 처리

### ⚠️ 이슈 및 해결
- **문제**: `AbstractKotlinCompile` 관련 빌드 에러
  - **해결**: `build.gradle.kts`에서 KSP 플러그인 버전을 `2.0.21-1.0.28`로 다운그레이드하여 호환성 확보
- **문제**: `FilterChipBorder` 파라미터 누락
  - **해결**: `enabled`, `selected` 파라미터 명시적 지정

---

## 📝 다음 작업

### 6단계: 데이터 레이어 개선

#### 간병 신청 목록 화면 (Issue #32 예정)
- [ ] Firestore 쿼리 추가: 내 신청 목록 조회
- [ ] `CareRequestListScreen` 구현
- [ ] `CareRequestListViewModel` 구현
- [ ] 리스트 아이템 UI (상태별 뱃지 표시)
- [ ] RoleSelectionScreen에 진입 버튼 추가

