# CareRequest (간병 신청)

## 📍 Firestore 경로
```
care_requests/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `id` | string | ✅ | 문서 ID (자동 생성, @Exclude) | "abc123..." |
| `serialNumber` | number | ✅ | 일련번호 (사람이 읽기 쉬운) | 10000000001 |
| `userId` | string | ✅ | 신청자 UID (Firebase Auth) | "bBSZfTWfWROoOnngD5DV9S2s4tK2" |
| `patientName` | string | ✅ | 환자 이름 | "김철수" |
| `patientAge` | number | ✅ | 환자 나이 | 75 |
| `patientGender` | string | ✅ | 환자 성별 | "남성" 또는 "여성" |
| `guardianName` | string | ✅ | 보호자 이름 | "김영희" |
| `patientCondition` | string | ✅ | 환자 상태/병명 | "뇌졸중 회복 중" |
| `careStartDate` | string | ✅ | 간병 시작일 | "2026-01-20" |
| `careEndDate` | string | ✅ | 간병 종료일 | "2026-01-27" |
| `location` | string | ✅ | 병원 위치 | "서울대학교병원 본관 501호" |
| `patientPhoneNumber` | string | ❌ | 환자 연락처 (선택) | "010-1111-2222" |
| `guardianPhoneNumber` | string | ✅ | 보호자 연락처 | "010-1234-5678" |
| `caregiverPhotoBase64` | string | ❌ | 간병사 사진 Base64 (선택) | "data:image/jpeg;base64,..." |
| `status` | string | ✅ | 신청 상태 | "pending" (기본값) |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-17 19:00:00 |

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class CareRequest(
    @get:Exclude val id: String = "",
    val serialNumber: Long = 0,
    val userId: String = "",
    val patientName: String = "",
    val patientAge: Int = 0,
    val patientGender: String = "",
    val guardianName: String = "",
    val patientCondition: String = "",
    val careStartDate: String = "",
    val careEndDate: String = "",
    val location: String = "",
    val patientPhoneNumber: String? = null,
    val guardianPhoneNumber: String = "",
    val caregiverPhotoBase64: String? = null,
    val status: String = "pending",
    val createdAt: Timestamp = Timestamp.now()
)
```

## 📝 예시 데이터

```json
{
  "serialNumber": 10000000001,
  "userId": "bBSZfTWfWROoOnngD5DV9S2s4tK2",
  "patientName": "김철수",
  "patientAge": 75,
  "patientGender": "남성",
  "guardianName": "김영희",
  "patientCondition": "뇌졸중 회복 중",
  "careStartDate": "2026-01-20",
  "careEndDate": "2026-01-27",
  "location": "서울대학교병원 본관 501호",
  "patientPhoneNumber": "010-1111-2222",
  "guardianPhoneNumber": "010-1234-5678",
  "status": "pending",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  }
}
```

## 📌 주요 변경사항 (2026-01-23)

### serialNumber 필드 추가
- **타입**: Long (11자리 숫자)
- **범위**: 10000000001 ~ 19999999999
- **화면 표시**: "100-0000-0001" 형식 (SerialNumberFormatter 사용)
- **목적**: 사람이 읽기 쉬운 번호, Firebase Console 검색 용이
- **생성**: Firestore Transaction으로 중복 방지

### id 필드 @Exclude
- Firestore 저장 시 제외 (`@get:Exclude`)
- 조회 시 문서 ID를 `id` 필드에 할당

## 🔄 상태 관리

### status 필드 값
- `pending`: 신청 대기 중 (기본값)
- `matched`: 매칭 완료
- `confirmed`: 매칭 확정
- `completed`: 간병 완료
- `cancelled`: 취소됨

## 🔍 쿼리 예시

### Kotlin (앱)
```kotlin
// 특정 사용자의 신청 목록 조회
firestore.collection("care_requests")
    .whereEqualTo("userId", currentUserId)
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()

// serialNumber로 검색
firestore.collection("care_requests")
    .whereEqualTo("serialNumber", 10000000001)
    .get()
    .await()

// pending 상태 신청 조회
firestore.collection("care_requests")
    .whereEqualTo("status", "pending")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()
```

---

**마지막 업데이트**: 2026-01-23
