## 📋 작업 내용

### 목표
앱 시작 시 표시되는 스플래시 화면을 구현합니다.

### 요구사항
- [ ] 간병24 로고 표시
- [ ] 앱 이름 표시
- [ ] 페이드인 애니메이션 효과
- [ ] 2초 후 자동으로 역할 선택 화면으로 이동
- [ ] 시니어 친화적 디자인 (큰 텍스트, 명확한 색상)

### 구현 내용
1. **SplashScreen.kt** 작성
   - Compose UI 구현
   - 애니메이션 효과 (alpha 애니메이션)
   - LaunchedEffect로 2초 딜레이 후 자동 이동

2. **디자인 요소**
   - 로고: 파란색 원형 배경 + 하트 아이콘
   - 앱 이름: "간병24" (24sp, Bold)
   - 배경색: 흰색
   - Primary 색상: #2196F3

3. **Navigation 연동**
   - 2초 후 RoleSelectionScreen으로 이동
   - popUpTo로 스플래시 화면 스택에서 제거

### 참고 문서
- [ScreenDesign.md](../docs/ScreenDesign.md#1-스플래시-화면-splashscreen)
- [TechnicalDesign.md](../docs/TechnicalDesign.md)

### 체크리스트
- [ ] SplashScreen.kt 작성
- [ ] 애니메이션 구현
- [ ] Navigation 연동
- [ ] 빌드 및 테스트
- [ ] PR 생성

## 🏷️ 라벨
- feature
- ui
- screen

## 📌 우선순위
High - 3단계 첫 번째 작업
