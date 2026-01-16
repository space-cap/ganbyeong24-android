# 최근 신청 환자 빠른 선택 기능

## 📋 개요
간병 신청 시 이전에 신청했던 환자명을 빠르게 선택할 수 있는 기능을 추가하여, 반복 신청 시 입력 편의성을 높입니다.

## 🎯 목표
- 같은 환자에 대한 반복 신청 시 입력 시간 단축
- 환자명 오타 방지 및 일관성 유지
- 시니어 사용자의 입력 부담 감소

## 📝 상세 작업 내용

### 1. 데이터베이스 설계
- [ ] Room Database에 `RecentPatient` Entity 추가
  - `id: Long` (Primary Key)
  - `patientName: String` (환자명)
  - `lastUsedAt: Long` (마지막 사용 시간)
  - `encryptedName: String` (암호화된 환자명 - 개인정보 보호)
- [ ] `RecentPatientDao` 생성
  - `getRecentPatients(limit: Int)` - 최근 사용 순으로 조회
  - `insertOrUpdate(patientName: String)` - 추가 또는 업데이트
  - `deletePatient(id: Long)` - 삭제
  - `clearAll()` - 전체 삭제

### 2. Repository 및 ViewModel 수정
- [ ] `CareRequestRepository`에 최근 환자 관련 메서드 추가
- [ ] `CareRequestViewModel`에 최근 환자 목록 상태 추가
  - `recentPatients: StateFlow<List<String>>`
  - `loadRecentPatients()` - 최근 환자 목록 로드
  - `savePatientName(name: String)` - 신청 완료 시 환자명 저장
  - `removeRecentPatient(name: String)` - 목록에서 제거

### 3. UI 컴포넌트 생성
- [ ] `RecentPatientChips` 컴포넌트 생성
  - 최근 환자명을 칩 버튼으로 표시
  - 각 칩 클릭 시 환자명 자동 입력
  - 칩 개수: 최대 5개
  - 칩 크기: 56dp 높이 (시니어 친화적)
  - 삭제 아이콘 (X) 추가

### 4. CareRequestScreen 수정
- [ ] Step 1 (환자 정보)에 `RecentPatientChips` 추가
  - 환자명 입력 필드 위에 배치
  - "최근 신청한 환자" 라벨 추가
  - 칩 선택 시 환자명 TextField에 자동 입력

### 5. 개인정보 보호
- [ ] 환자명 암호화 구현
  - Android Keystore 사용
  - AES 암호화 적용
- [ ] 앱 삭제 시 데이터 자동 삭제
- [ ] 설정에서 수동 삭제 옵션 제공

## 🎨 UI 디자인

```
Step 1: 환자 정보

최근 신청한 환자
┌─────────────────────────────────┐
│ [홍길동 ×] [김철수 ×] [이영희 ×] │
└─────────────────────────────────┘

환자명 *
┌─────────────────────────────────┐
│                                  │
└─────────────────────────────────┘
```

## ✅ 완료 조건
- [ ] 최근 환자 목록이 정상적으로 표시됨
- [ ] 칩 클릭 시 환자명이 자동으로 입력됨
- [ ] 삭제 버튼(X)으로 목록에서 제거 가능
- [ ] 신청 완료 시 환자명이 자동으로 저장됨
- [ ] 환자명이 암호화되어 저장됨
- [ ] 최대 5개까지만 표시됨 (오래된 것부터 자동 삭제)
- [ ] 빌드 성공 및 테스트 통과

## 🏷️ 라벨
`enhancement`, `ui/ux`, `database`, `privacy`

## 📌 우선순위
Medium - 편의성 개선 기능

## 🔗 관련 이슈
- #28 (시니어 친화적 간병 신청 UI 개선)
