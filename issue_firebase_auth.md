## 🎯 작업 내용
Firebase Authentication 구현

## 📋 상세 설명
사용자 인증 기능을 추가하여 사용자별 데이터 관리를 가능하게 합니다.

## ✅ 구현 사항

### Firebase 설정
- [ ] Firebase Console에서 Authentication 활성화
- [ ] build.gradle.kts에 firebase-auth 의존성 추가

### 데이터 모델 업데이트
- [ ] CareRequest에 userId 필드 추가
- [ ] Caregiver에 userId 필드 추가

### Repository 구현
- [ ] AuthRepository 인터페이스 생성
- [ ] AuthRepositoryImpl 구현
  - 로그인 기능
  - 회원가입 기능
  - 로그아웃 기능
  - 현재 사용자 확인

### ViewModel 구현
- [ ] LoginState, LoginViewModel
- [ ] SignupState, SignupViewModel

### UI 구현
- [ ] LoginScreen.kt 생성
- [ ] SignupScreen.kt 생성

### Navigation 업데이트
- [ ] Screen.kt에 Login, Signup 추가
- [ ] NavGraph.kt 업데이트
  - SplashScreen → LoginScreen (로그인 안 된 경우)
  - SplashScreen → RoleSelectionScreen (로그인 된 경우)

### 기존 코드 수정
- [ ] CareRequestViewModel에서 userId 자동 추가
- [ ] CaregiverRegistrationViewModel에서 userId 자동 추가
- [ ] Koin 모듈 업데이트

## 🎨 디자인 요구사항
- Material3 테마 적용
- 시니어 친화적 큰 폰트
- 명확한 에러 메시지

## 📝 참고 문서
- [Firebase Authentication 문서](https://firebase.google.com/docs/auth/android/start)

## 🔗 관련 이슈
- #19 (CareRequestScreen)
- #21 (CaregiverRegistrationScreen)
