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

### 🚧 진행 중인 작업
- SplashScreen 마무리
  - [ ] 로고 이미지 추가 (나중에)
  - [ ] Git commit & push
  - [ ] PR 생성 및 머지

---

## 🎯 다음 작업 (2026-01-15 오후 예정)

### 3단계: 화면 개발 (계속)

#### SplashScreen 마무리
- [ ] Git commit & push
- [ ] PR 생성 (`feature/splash-screen` → `develop`)
- [ ] PR 머지 및 브랜치 정리

#### RoleSelectionScreen 개발
- [ ] GitHub 이슈 등록
- [ ] feature/role-selection 브랜치 생성
- [ ] 역할 선택 UI 구현
- [ ] 보호자/간병사 버튼 디자인

#### CareRequestScreen 개발 (시간 되면)
- [ ] 간병 신청 폼 구현
- [ ] 입력 필드 및 유효성 검사

---

## 📊 진행 상황

### 전체 로드맵 (5단계)
- ✅ 1단계: 프로젝트 초기 설정 (100%)
- ✅ 2단계: 기반 구축 (100%)
- 🚧 3단계: 화면 개발 (20% - SplashScreen 완료)
- ⏳ 4단계: 데이터 레이어 (0%)
- ⏳ 5단계: 테스트 및 배포 (0%)

### GitHub Issues
- ✅ Closed: #1, #2, #3, #4, #5, #6, #13
- � In Progress: #15 (SplashScreen)
- �📝 Next: RoleSelectionScreen, CareRequestScreen

---

## 💡 메모

### 프로젝트 구조
```
com.ezlevup.ganbyeong24/
├── di/                          # ✅ Koin 모듈
├── data/
│   ├── model/                   # CareRequest, Caregiver
│   └── repository/              # Repository
├── presentation/
│   ├── theme/                   # ✅ Color, Type, Theme
│   ├── components/              # ✅ Button, TextField
│   ├── navigation/              # ✅ Screen, NavGraph
│   └── screens/
│       ├── splash/              # ✅ SplashScreen (완료)
│       ├── role/
│       ├── care_request/
│       ├── caregiver/
│       └── result/
└── util/
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

**마지막 업데이트**: 2026-01-15 13:47

