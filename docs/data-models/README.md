# 간병24 데이터 모델 문서

## 📋 개요

이 문서는 간병24 앱과 웹 플랫폼에서 사용하는 Firebase Firestore 데이터 모델을 정의합니다.

## 🗂 컬렉션 목록

| 컬렉션 이름 | 설명 | 문서 |
|------------|------|------|
| `care_requests` | 간병 신청 정보 | [CareRequest.md](./CareRequest.md) |
| `caregivers` | 간병사 등록 정보 | [Caregiver.md](./Caregiver.md) |
| `users` | 사용자 정보 (Soft Delete) | [User.md](./User.md) |

## 🔥 Firebase 프로젝트 정보

- **프로젝트 ID**: `ganbyeong24` (실제 프로젝트 ID로 변경 필요)
- **Database**: Cloud Firestore
- **Authentication**: Email/Password

## 📱 사용 플랫폼

- **Android 앱**: Kotlin + Firebase SDK
- **웹 플랫폼**: React + TypeScript + Firebase SDK

## 🔒 보안 규칙

Firestore Security Rules는 별도로 관리됩니다.

현재 규칙:
- 인증된 사용자만 읽기/쓰기 가능 (2026년 2월 13일까지 테스트용)
- 프로덕션 배포 전 규칙 강화 필요

## 📝 타입 정의

각 데이터 모델 문서에는 다음이 포함됩니다:
- Firestore 경로
- 필드 구조 (타입, 필수 여부, 설명)
- TypeScript 타입 정의
- Kotlin 데이터 클래스 정의
- 예시 데이터

## 🔄 데이터 동기화

앱과 웹은 **같은 Firestore 데이터베이스**를 공유합니다:
- 앱에서 생성한 데이터를 웹에서 조회 가능
- 웹에서 생성한 데이터를 앱에서 조회 가능
- 실시간 업데이트 지원

## 📞 문의

데이터 모델 관련 문의사항이 있으시면 개발팀에 연락 주세요.
