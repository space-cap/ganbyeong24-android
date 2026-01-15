## 🎯 작업 내용
CaregiverRegistrationScreen 구현 - 간병사 등록 화면

## 📋 상세 설명
간병사가 자신의 정보를 등록할 수 있는 화면을 구현합니다.

## ✅ 구현 사항

### 데이터 레이어
- [ ] Caregiver 데이터 모델 생성
- [ ] CaregiverRepository 인터페이스 생성
- [ ] CaregiverRepositoryImpl 구현 (Firestore 연동)
- [ ] Koin 모듈에 Repository 등록

### Presentation 레이어
- [ ] CaregiverRegistrationState 데이터 클래스 생성
- [ ] CaregiverRegistrationViewModel 구현
  - StateFlow 상태 관리
  - 입력 핸들러 함수들
  - 유효성 검사 로직
  - Firestore 저장 로직
- [ ] Koin 모듈에 ViewModel 등록

### UI 구현
- [ ] CaregiverRegistrationScreen.kt 파일 생성
- [ ] TopAppBar (뒤로가기 버튼)
- [ ] 입력 필드 구현
  - 이름 (필수)
  - 경력 (필수)
  - 자격증 (필수)
  - 가능 지역 (필수)
  - 연락처 (필수)
- [ ] 등록하기 버튼
- [ ] 에러 다이얼로그
- [ ] 로딩 상태 처리

### Navigation
- [ ] NavGraph.kt 업데이트
  - CaregiverRegistrationScreenPlaceholder를 실제 화면으로 교체

## 🎨 디자인 요구사항
- Material3 테마 적용
- 시니어 친화적 큰 폰트
- 명확한 필수 필드 표시
- 유효성 검사 에러 메시지 표시

## 📝 참고 문서
- [ScreenDesign.md](./docs/ScreenDesign.md)
- [TechnicalDesign.md](./docs/TechnicalDesign.md)

## 🔗 관련 이슈
- #19 (CareRequestScreen 구현)
