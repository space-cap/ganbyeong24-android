# Caregiver (간병사 등록)

## 📍 Firestore 경로
```
caregivers/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `id` | string | ✅ | 문서 ID (자동 생성) | "xyz789..." |
| `userId` | string | ✅ | 등록자 UID (Firebase Auth) | "abc123..." |
| `name` | string | ✅ | 간병사 이름 | "이영희" |
| `experience` | string | ✅ | 경력 | "5년" |
| `certificates` | string | ✅ | 자격증 정보 | "요양보호사 1급" |
| `availableRegions` | string | ✅ | 가능 지역 | "서울, 경기" |
| `phoneNumber` | string | ✅ | 연락처 | "010-9876-5432" |
| `status` | string | ✅ | 등록 상태 | "pending" (기본값) |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-17 19:00:00 |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface Caregiver {
  id: string;
  userId: string;
  name: string;
  experience: string;
  certificates: string;
  availableRegions: string;
  phoneNumber: string;
  status: string;  // "pending" | "active" | "inactive"
  createdAt: Timestamp;
}

// Firestore에서 읽을 때 (documentId는 이미 id 필드에 포함)
type CaregiverDocument = Caregiver;
```

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp

data class Caregiver(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val experience: String = "",
    val certificates: String = "",
    val availableRegions: String = "",
    val phoneNumber: String = "",
    val status: String = "pending",
    val createdAt: Timestamp = Timestamp.now()
)
```

## 📝 예시 데이터

```json
{
  "id": "xyz789abc123",
  "userId": "abc123def456ghi789",
  "name": "이영희",
  "experience": "5년",
  "certificates": "요양보호사 1급",
  "availableRegions": "서울, 경기",
  "phoneNumber": "010-9876-5432",
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

// 특정 지역 간병사 조회
const q = query(
  collection(db, 'caregivers'),
  orderBy('createdAt', 'desc')
);

const snapshot = await getDocs(q);
const caregivers = snapshot.docs.map(doc => ({
  ...doc.data(),
  id: doc.id
} as Caregiver));
```

### Kotlin (앱)
```kotlin
// 간병사 목록 조회
firestore.collection("caregivers")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()
```

## 📌 주의사항

1. **전화번호 형식**: "010-XXXX-XXXX" 형식으로 저장
2. **경력**: 문자열 형식 (예: "5년", "10년")
3. **자격증**: 문자열 형식 (예: "요양보호사 1급", "간호조무사")
4. **가능 지역**: 쉼표로 구분된 문자열 (예: "서울, 경기, 인천")
5. **status 값**: "pending" (기본값), "active", "inactive"

## 🔄 상태 관리

### status 필드 값
- `pending`: 등록 대기 중 (기본값)
- `active`: 활동 중
- `inactive`: 휴면

### 향후 확장 가능 필드
- `rating`: 평점 (1-5)
- `reviewCount`: 리뷰 개수
- `matchCount`: 매칭 완료 횟수
- `updatedAt`: 마지막 수정 일시
- `desiredSalary`: 희망 급여
- `introduction`: 자기소개
