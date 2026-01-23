# CareRequest (간병 신청)

## 📍 Firestore 경로
```
care_requests/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `id` | string | ✅ | 문서 ID (자동 생성) | "abc123..." |
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
| `status` | string | ✅ | 신청 상태 | "pending" (기본값) |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-17 19:00:00 |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface CareRequest {
  id: string;
  userId: string;
  patientName: string;
  patientAge: number;
  patientGender: string;  // "남성" | "여성"
  guardianName: string;
  patientCondition: string;
  careStartDate: string;  // "YYYY-MM-DD" 형식
  careEndDate: string;    // "YYYY-MM-DD" 형식
  location: string;
  patientPhoneNumber?: string | null;
  guardianPhoneNumber: string;
  status: string;  // "pending" | "confirmed" | "completed" | "cancelled"
  createdAt: Timestamp;
}

// Firestore에서 읽을 때 (documentId는 이미 id 필드에 포함)
type CareRequestDocument = CareRequest;
```

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp

data class CareRequest(
    val id: String = "",
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
    val status: String = "pending",
    val createdAt: Timestamp = Timestamp.now()
)
```

## 📝 예시 데이터

```json
{
  "id": "abc123def456",
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

## 🔍 쿼리 예시

### TypeScript (웹)
```typescript
import { collection, query, where, orderBy, getDocs } from 'firebase/firestore';

// 특정 사용자의 신청 목록 조회
const q = query(
  collection(db, 'care_requests'),
  where('userId', '==', currentUserId),
  orderBy('createdAt', 'desc')
);

const snapshot = await getDocs(q);
const requests = snapshot.docs.map(doc => ({
  ...doc.data(),
  id: doc.id  // id는 이미 데이터에 포함되어 있지만, documentId로 덮어쓰기
} as CareRequest));
```

### Kotlin (앱)
```kotlin
// 특정 사용자의 신청 목록 조회
firestore.collection("care_requests")
    .whereEqualTo("userId", currentUserId)
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()
```

## 📌 주의사항

1. **날짜 형식**: `careStartDate`, `careEndDate`는 문자열 형식 ("YYYY-MM-DD")
2. **전화번호 형식**: "010-XXXX-XXXX" 형식으로 저장
3. **status 값**: "pending" (기본값), "confirmed", "completed", "cancelled"
4. **patientAge**: 1-120 범위의 정수 값
5. **patientGender**: "남성" 또는 "여성" 값만 허용
6. **patientPhoneNumber**: 선택 필드이므로 `null` 또는 `undefined` 가능
7. **id 필드**: Firestore 문서 ID와 동일하게 저장 (중복이지만 쿼리 편의성을 위해)

## 🔄 상태 관리

### status 필드 값
- `pending`: 신청 대기 중 (기본값)
- `confirmed`: 매칭 확정
- `completed`: 간병 완료
- `cancelled`: 취소됨

### 향후 확장 가능 필드
- `matchedCaregiverId`: 매칭된 간병사 ID
- `updatedAt`: 마지막 수정 일시
- `notes`: 관리자 메모
