# Caregiver (간병사 등록)

## 📍 Firestore 경로
```
caregivers/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `name` | string | ✅ | 간병사 이름 | "이영희" |
| `phone` | string | ✅ | 연락처 | "010-9876-5432" |
| `experience` | number | ✅ | 경력 (년) | 5 |
| `hasCertificate` | boolean | ✅ | 자격증 보유 여부 | true |
| `availableRegions` | string | ✅ | 가능 지역 | "서울, 경기" |
| `desiredSalary` | string | ✅ | 희망 급여 | "일 15만원" |
| `introduction` | string | ✅ | 자기소개 | "5년 경력의..." |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-17 19:00:00 |
| `userId` | string | ✅ | 등록자 UID (Firebase Auth) | "xyz789..." |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface Caregiver {
  name: string;
  phone: string;
  experience: number;
  hasCertificate: boolean;
  availableRegions: string;
  desiredSalary: string;
  introduction: string;
  createdAt: Timestamp;
  userId: string;
}

// Firestore에서 읽을 때 (documentId 포함)
interface CaregiverWithId extends Caregiver {
  id: string;
}
```

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp

data class Caregiver(
    val name: String = "",
    val phone: String = "",
    val experience: Int = 0,
    val hasCertificate: Boolean = false,
    val availableRegions: String = "",
    val desiredSalary: String = "",
    val introduction: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val userId: String = ""
)
```

## 📝 예시 데이터

```json
{
  "name": "이영희",
  "phone": "010-9876-5432",
  "experience": 5,
  "hasCertificate": true,
  "availableRegions": "서울, 경기",
  "desiredSalary": "일 15만원",
  "introduction": "5년 경력의 전문 간병사입니다. 환자분들을 가족처럼 돌보겠습니다.",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  },
  "userId": "xyz789abc123def456"
}
```

## 🔍 쿼리 예시

### TypeScript (웹)
```typescript
import { collection, query, where, orderBy, getDocs } from 'firebase/firestore';

// 자격증 보유 간병사 조회
const q = query(
  collection(db, 'caregivers'),
  where('hasCertificate', '==', true),
  orderBy('experience', 'desc')
);

const snapshot = await getDocs(q);
const caregivers = snapshot.docs.map(doc => ({
  id: doc.id,
  ...doc.data()
} as CaregiverWithId));
```

### Kotlin (앱)
```kotlin
// 자격증 보유 간병사 조회
firestore.collection("caregivers")
    .whereEqualTo("hasCertificate", true)
    .orderBy("experience", Query.Direction.DESCENDING)
    .get()
    .await()
```

## 📌 주의사항

1. **전화번호 형식**: "010-XXXX-XXXX" 형식으로 저장
2. **경력**: 정수형 (년 단위)
3. **가능 지역**: 쉼표로 구분된 문자열 (예: "서울, 경기, 인천")
4. **희망 급여**: 자유 형식 문자열 (예: "일 15만원", "시간당 2만원")

## 🔄 상태 관리 (향후 확장)

현재는 상태 필드가 없지만, 향후 추가 예정:
- `status`: "대기중" | "활동중" | "휴면"
- `rating`: 평점 (1-5)
- `reviewCount`: 리뷰 개수
- `matchCount`: 매칭 완료 횟수
- `updatedAt`: 마지막 수정 일시
