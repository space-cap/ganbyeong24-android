## 🎯 작업 내용
CareRequestScreen 구현 - 간병 신청 화면

## 📋 상세 설명
보호자가 간병 서비스를 신청할 수 있는 화면을 구현합니다.

## ✅ 구현 사항

### 데이터 레이어
- [ ] CareRequest 데이터 모델 생성
- [ ] CareRequestRepository 인터페이스 생성
- [ ] CareRequestRepositoryImpl 구현 (Firestore 연동)
- [ ] Koin 모듈에 Repository 등록

### Presentation 레이어
- [ ] CareRequestState 데이터 클래스 생성
- [ ] CareRequestViewModel 구현
  - StateFlow 상태 관리
  - 입력 핸들러 함수들
  - 유효성 검사 로직
  - Firestore 저장 로직
- [ ] Koin 모듈에 ViewModel 등록

### UI 구현
- [ ] CareRequestScreen.kt 파일 생성
- [ ] TopAppBar (뒤로가기 버튼)
- [ ] 입력 필드 구현
  - 환자명 (필수)
  - 보호자명 (필수)
  - 환자 상태 (필수)
  - 간병 기간 시작일 (필수)
  - 간병 기간 종료일 (필수)
  - 위치 (필수)
  - 환자 연락처 (선택)
  - 보호자 연락처 (필수)
- [ ] 신청하기 버튼
- [ ] 에러 다이얼로그
- [ ] 로딩 상태 처리

### Navigation
- [ ] NavGraph.kt 업데이트
  - CareRequestScreenPlaceholder를 실제 화면으로 교체

## 🎨 디자인 요구사항
- Material3 테마 적용
- 시니어 친화적 큰 폰트
- 명확한 필수/선택 필드 표시
- 유효성 검사 에러 메시지 표시

## 📝 참고 문서
- [ScreenDesign.md](./docs/ScreenDesign.md)
- [TechnicalDesign.md](./docs/TechnicalDesign.md)

## 🔗 관련 이슈
- #17 (RoleSelectionScreen 구현)
