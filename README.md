# 간병24 (Ganbyeong24) 📱

> 믿을 수 있는 간병 매칭 서비스 Android 앱

간병이 필요한 보호자와 간병사를 연결하는 모바일 플랫폼입니다.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-8.0+-green.svg)](https://developer.android.com)
[![Firebase](https://img.shields.io/badge/Firebase-Latest-orange.svg)](https://firebase.google.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [빌드 및 실행](#-빌드-및-실행)
- [문서](#-문서)
- [기여하기](#-기여하기)
- [라이선스](#-라이선스)

## ✨ 주요 기능

### 🔐 사용자 인증
- Firebase Authentication 기반 이메일/비밀번호 로그인
- 회원가입 및 로그인
- 역할 기반 접근 제어 (보호자/간병사/관리자)
- 로그아웃 및 회원 탈퇴 (Soft Delete)

### 👥 역할 선택
- **보호자 모드**: 간병 서비스 신청 및 내역 조회
- **간병사 모드**: 간병사 등록
- **관리자 모드**: 전체 시스템 관리

### 📝 간병 신청 (보호자)
- 환자 정보 입력 (이름, 나이, 성별)
- 병원 및 병실 정보
- 간병 시작일 및 기간 설정
- 보호자 연락처 및 특이사항
- 내 신청 내역 조회 (상태별 필터링)
- Firebase Firestore에 실시간 저장
- **SerialNumber 시스템**: 사람이 읽기 쉬운 신청 번호 (예: `100-0000-0001`)

### 👨‍⚕️ 간병사 등록
- 프로필 사진 업로드
- 간병사 정보 입력 (이름, 성별, 연락처)
- 경력 선택 (드롭다운)
- 자격증 다중 선택 (칩)
- 가능 지역 다중 선택 (칩)
- 희망 급여 및 자기소개
- Firebase Firestore에 실시간 저장
- **SerialNumber 시스템**: 사람이 읽기 쉬운 간병사 번호 (예: `200-0000-0001`)

### 👨‍💼 관리자 시스템
- **간병 신청 관리**: 전체 신청 조회 및 상태별 필터링
- **간병사 관리**: 전체 간병사 조회 및 다중 필터링 (지역/자격증/경력)
- **매칭 관리**: 3단계 매칭 프로세스
  - Step 1: 간병 신청 선택
  - Step 2: 간병사 선택 (지역 기반 자동 필터링)
  - Step 3: 매칭 확인 및 생성
- **자동 상태 업데이트**: 매칭 생성 시 신청 상태 자동 변경
- **SerialNumber 시스템**: 매칭 번호 (예: `300-0000-0001`)

### ⚙️ 프로필 관리
- 사용자 정보 조회 (이메일, 가입일)
- 로그아웃
- 회원 탈퇴 (Soft Delete 방식)
- 앱 버전 정보

### 🎨 시니어 친화적 UI
- 큰 글씨 및 명확한 버튼
- 직관적인 네비게이션
- 접근성 고려 디자인
- Material Design 3 적용

## 🛠 기술 스택

### Language & Framework
- **Kotlin** - 100% Kotlin으로 작성
- **Jetpack Compose** - 선언형 UI 프레임워크
- **Material Design 3** - 최신 디자인 시스템

### Architecture & Libraries
- **MVVM Architecture** - ViewModel + StateFlow
- **Koin** - 의존성 주입 (DI)
- **Kotlin Coroutines** - 비동기 처리
- **Navigation Compose** - 화면 네비게이션
- **Room Database** - 로컬 데이터 저장 (최근 환자 목록)
- **Coil** - 이미지 로딩 라이브러리

### Backend & Database
- **Firebase Authentication** - 사용자 인증
- **Firebase Firestore** - NoSQL 클라우드 데이터베이스
- **Firebase SDK** - 실시간 데이터 동기화

### Build & Tools
- **Gradle (Kotlin DSL)** - 빌드 시스템
- **Android Studio** - IDE
- **Git** - 버전 관리

## 📁 프로젝트 구조

```
app/src/main/java/com/ezlevup/ganbyeong24/
├── data/
│   ├── model/              # 데이터 모델
│   │   ├── User.kt         # 사용자 (role 필드 포함)
│   │   ├── CareRequest.kt  # 간병 신청 (serialNumber 포함)
│   │   ├── Caregiver.kt    # 간병사 (serialNumber 포함)
│   │   └── Match.kt        # 매칭 (serialNumber 포함)
│   └── repository/         # Repository 패턴 구현
│       ├── AuthRepository
│       ├── CareRequestRepository
│       ├── CaregiverRepository
│       └── MatchRepository
├── di/
│   └── AppModule.kt        # Koin DI 모듈
├── presentation/
│   ├── navigation/         # Navigation Graph
│   ├── components/         # 공통 UI 컴포넌트
│   └── screens/
│       ├── auth/           # 로그인/회원가입 화면
│       ├── role/           # 역할 선택 화면
│       ├── care_request/   # 간병 신청 화면
│       ├── caregiver/      # 간병사 등록 화면
│       ├── profile/        # 프로필 화면
│       └── admin/          # 관리자 화면
│           ├── AdminDashboardScreen.kt
│           ├── care_request/  # 간병 신청 관리
│           ├── caregiver/     # 간병사 관리
│           └── match/         # 매칭 관리
├── util/
│   └── SerialNumberFormatter.kt  # 일련번호 포맷팅
└── MainActivity.kt
```

## 🚀 시작하기

### 필수 요구사항

- **Android Studio**: Hedgehog (2023.1.1) 이상
- **JDK**: 17 이상
- **Android SDK**: API 26 (Android 8.0) 이상
- **Firebase 프로젝트**: [Firebase Console](https://console.firebase.google.com/)에서 생성

### Firebase 설정

1. Firebase Console에서 프로젝트 생성
2. Android 앱 추가 (패키지명: `com.ezlevup.ganbyeong24`)
3. `google-services.json` 다운로드
4. `app/` 디렉토리에 `google-services.json` 배치
5. Firebase Authentication 활성화 (이메일/비밀번호)
6. Firestore Database 생성

자세한 설정 방법은 [Firebase 설정 가이드](docs/Firebase_Setup_Guide.md)를 참조하세요.

## 🔨 빌드 및 실행

### 1. 프로젝트 클론

```bash
git clone https://github.com/yourusername/ganbyeong24-android.git
cd ganbyeong24-android
```

### 2. Firebase 설정 파일 추가

`app/google-services.json` 파일을 추가합니다.

### 3. Android Studio에서 열기

Android Studio에서 프로젝트를 열고 Gradle 동기화를 기다립니다.

### 4. 빌드 및 실행

```bash
# Debug 빌드
./gradlew assembleDebug

# Release 빌드
./gradlew assembleRelease

# 에뮬레이터/디바이스에 설치 및 실행
./gradlew installDebug
```

또는 Android Studio에서 `Run` 버튼을 클릭합니다.

## 📚 문서

프로젝트 관련 상세 문서는 `docs/` 디렉토리에서 확인할 수 있습니다:

- **[PRD.md](docs/PRD.md)** - 프로젝트 요구사항 정의서 (Android 앱)
- **[Firebase_Setup_Guide.md](docs/Firebase_Setup_Guide.md)** - Firebase 설정 가이드
- **[데이터 모델](docs/data-models/)** - Firestore 데이터 모델 문서
  - [CareRequest.md](docs/data-models/CareRequest.md) - 간병 신청 모델
  - [Caregiver.md](docs/data-models/Caregiver.md) - 간병사 모델
  - [User.md](docs/data-models/User.md) - 사용자 모델
- **[웹 플랫폼 PRD](docs/web/PRD_Web_Platform.md)** - 웹 플랫폼 기획서

## 🗂 데이터베이스 구조

### Firestore Collections

| 컬렉션 | 설명 | 문서 |
|--------|------|------|
| `users` | 사용자 정보 (role 필드 포함) | [User.md](docs/data-models/User.md) |
| `care_requests` | 간병 신청 정보 (serialNumber 포함) | [CareRequest.md](docs/data-models/CareRequest.md) |
| `caregivers` | 간병사 등록 정보 (serialNumber 포함) | [Caregiver.md](docs/data-models/Caregiver.md) |
| `matches` | 매칭 정보 (serialNumber 포함) | [Match.md](docs/data-models/Match.md) |
| `counters` | SerialNumber 카운터 | - |

### SerialNumber 시스템

사람이 읽기 쉬운 일련번호 체계를 사용합니다:

- **간병 신청**: `100-0000-0001` (10000000001부터 시작)
- **간병사**: `200-0000-0001` (20000000001부터 시작)
- **매칭**: `300-0000-0001` (30000000001부터 시작)

Firestore Transaction을 사용하여 중복 없이 일련번호를 생성합니다.

## 🎯 로드맵

### ✅ 완료된 기능
- [x] Firebase Authentication 연동
- [x] 로그인/회원가입 화면
- [x] 역할 기반 접근 제어 (보호자/간병사/관리자)
- [x] 역할 선택 화면
- [x] 간병 신청 화면 (3단계 프로세스)
- [x] 간병 신청 내역 조회
- [x] 간병사 등록 화면 (프로필 사진, 다중 선택)
- [x] 프로필 화면 (로그아웃, 회원 탈퇴)
- [x] **관리자 시스템**
  - [x] 관리자 대시보드
  - [x] 간병 신청 관리 (전체 조회, 상태별 필터링)
  - [x] 간병사 관리 (전체 조회, 다중 필터링)
  - [x] 매칭 관리 (3단계 프로세스, 자동 상태 업데이트)
- [x] **SerialNumber 시스템** (일련번호 자동 생성)
- [x] Room Database (최근 환자 빠른 선택)
- [x] Soft Delete 구현
- [x] MVVM 아키텍처 적용
- [x] Koin DI 적용

### 📋 계획 중
- [ ] 사용자 매칭 내역 조회 (보호자/간병사)
- [ ] 푸시 알림 (Firebase Cloud Messaging)
- [ ] 리뷰 및 평점 시스템
- [ ] 웹 플랫폼 개발 (React + Vite)
- [ ] 자동 매칭 알고리즘

## 🤝 기여하기

기여는 언제나 환영합니다! 다음 단계를 따라주세요:

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 👨‍💻 개발자

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 감사의 말

- [Firebase](https://firebase.google.com/) - 백엔드 서비스
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI 프레임워크
- [Koin](https://insert-koin.io/) - 의존성 주입

---

<p align="center">Made with ❤️ for better caregiving services</p>
