## 🎯 작업 내용
RoleSelectionScreen 구현 - 역할 선택 화면

## 📋 상세 설명
사용자가 보호자 또는 간병사 역할을 선택할 수 있는 화면을 구현합니다.

## ✅ 구현 사항
- [ ] RoleSelectionScreen.kt 파일 생성
- [ ] 화면 UI 구성
  - 제목: "역할을 선택해주세요"
  - 설명 텍스트: 간단한 안내 문구
  - "보호자" 버튼 (간병 신청)
  - "간병사" 버튼 (간병사 등록)
  - 각 버튼에 적절한 아이콘 추가
- [ ] 네비게이션 연결
  - 보호자 선택 → CareRequestScreen으로 이동
  - 간병사 선택 → CaregiverRegistrationScreen으로 이동
- [ ] NavGraph.kt 업데이트
  - RoleSelectionScreenPlaceholder를 실제 RoleSelectionScreen으로 교체

## 🎨 디자인 요구사항
- Material3 테마 적용
- 시니어 친화적 큰 버튼 (높이 56dp 이상)
- 명확한 아이콘과 텍스트
- 적절한 간격과 여백

## 📝 참고 문서
- [ScreenDesign.md](./docs/ScreenDesign.md)
- [TechnicalDesign.md](./docs/TechnicalDesign.md)

## 🔗 관련 이슈
- #15 (SplashScreen 구현)
