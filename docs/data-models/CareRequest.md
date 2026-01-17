# CareRequest (간병 신청)

## 📍 Firestore 경로
```
care_requests/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `patientName` | string | ✅ | 환자 이름 | "김철수" |
| `patientAge` | number | ✅ | 환자 나이 | 75 |
| `patientGender` | string | ✅ | 환자 성별 | "남성" 또는 "여성" |
| `hospitalName` | string | ✅ | 병원 이름 | "서울대학교병원" |
| `roomInfo` | string | ✅ | 병실 정보 | "본관 501호" |
| `startDate` | Timestamp | ✅ | 간병 시작일 | 2026-01-20 |
| `duration` | number | ✅ | 간병 기간 (일) | 7 |
| `guardianPhone` | string | ✅ | 보호자 연락처 | "010-1234-5678" |
| `specialNotes` | string | ❌ | 특이사항 | "식사 보조 필요" |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-17 19:00:00 |
| `userId` | string | ✅ | 신청자 UID (Firebase Auth) | "abc123..." |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface CareRequest {
  patientName: string;
  patientAge: number;
  patientGender: '남성' | '여성';
  hospitalName: string;
  roomInfo: string;
  startDate: Timestamp;
  duration: number;
  guardianPhone: string;
  specialNotes?: string;
  createdAt: Timestamp;
  userId: string;
}

// Firestore에서 읽을 때 (documentId 포함)
interface CareRequestWithId extends CareRequest {
  id: string;
}
```

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp

data class CareRequest(
    val patientName: String = "",
    val patientAge: Int = 0,
    val patientGender: String = "",
    val hospitalName: String = "",
    val roomInfo: String = "",
    val startDate: Timestamp = Timestamp.now(),
    val duration: Int = 0,
    val guardianPhone: String = "",
    val specialNotes: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val userId: String = ""
)
```

## 📝 예시 데이터

```json
{
  "patientName": "김철수",
  "patientAge": 75,
  "patientGender": "남성",
  "hospitalName": "서울대학교병원",
  "roomInfo": "본관 501호",
  "startDate": {
    "_seconds": 1737360000,
    "_nanoseconds": 0
  },
  "duration": 7,
  "guardianPhone": "010-1234-5678",
  "specialNotes": "식사 보조 필요",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  },
  "userId": "bBSZfTWfWROoOnngD5DV9S2s4tK2"
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
  id: doc.id,
  ...doc.data()
} as CareRequestWithId));
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

1. **전화번호 형식**: "010-XXXX-XXXX" 형식으로 저장
2. **성별 값**: 정확히 "남성" 또는 "여성"만 허용
3. **날짜 타입**: Firebase Timestamp 사용 (Date 객체 아님)
4. **특이사항**: 선택 필드이므로 빈 문자열 또는 undefined 가능

## 🔄 상태 관리 (향후 확장)

현재는 상태 필드가 없지만, 향후 추가 예정:
- `status`: "대기중" | "상담중" | "매칭완료" | "취소"
- `matchedCaregiverId`: 매칭된 간병사 ID
- `updatedAt`: 마지막 수정 일시
