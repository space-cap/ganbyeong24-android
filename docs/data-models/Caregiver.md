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
| `gender` | string | ✅ | 성별 | "남성" 또는 "여성" |
| `experience` | string | ✅ | 경력 | "5~10년" |
| `certificates` | array | ✅ | 자격증 목록 | ["요양보호사", "간호사"] |
| `availableRegions` | array | ✅ | 가능 지역 목록 | ["서울", "경기", "인천"] |
| `phoneNumber` | string | ✅ | 연락처 | "01098765432" |
| `photoBase64` | string | ❌ | 프로필 사진 (Base64) | "data:image/jpeg;base64,/9j/4AAQ..." |
| `status` | string | ✅ | 등록 상태 | "pending" (기본값) |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-22 23:00:00 |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface Caregiver {
  id: string;
  userId: string;
  name: string;
  gender: string;  // "남성" | "여성"
  experience: string;
  certificates: string[];  // 배열로 변경
  availableRegions: string[];  // 배열로 변경
  phoneNumber: string;
  photoBase64?: string;  // 선택적 필드
  status: string;  // "pending" | "approved" | "rejected"
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
    val gender: String = "",
    val experience: String = "",
    val certificates: List<String> = emptyList(),
    val availableRegions: List<String> = emptyList(),
    val phoneNumber: String = "",
    val photoBase64: String? = null,
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
  "gender": "여성",
  "experience": "5~10년",
  "certificates": ["요양보호사", "간호사"],
  "availableRegions": ["서울", "경기", "인천"],
  "phoneNumber": "01098765432",
  "photoBase64": "/9j/4AAQSkZJRgABAQAAAQABAAD...",
  "status": "pending",
  "createdAt": {
    "_seconds": 1737561600,
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
  where('availableRegions', 'array-contains', '서울'),
  where('status', '==', 'approved'),
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
// 승인된 간병사 목록 조회
firestore.collection("caregivers")
    .whereEqualTo("status", "approved")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()

// 특정 지역 간병사 조회
firestore.collection("caregivers")
    .whereArrayContains("availableRegions", "서울")
    .whereEqualTo("status", "approved")
    .get()
    .await()
```

## 📌 주의사항

1. **전화번호 형식**: 숫자만 저장 (예: "01098765432")
2. **성별**: "남성" 또는 "여성"
3. **경력**: 드롭다운 선택 값 (예: "1년 미만", "1~3년", "3~5년", "5~10년", "10년 이상")
4. **자격증**: 배열 형식, 다중 선택 가능 (예: ["요양보호사", "간호사", "간호조무사"])
5. **가능 지역**: 배열 형식, 다중 선택 가능 (예: ["서울", "경기", "인천"])
6. **프로필 사진**: Base64 인코딩 문자열, 200x200px JPEG 80% 압축 (~15-25KB)
7. **status 값**: "pending" (기본값), "approved", "rejected"

## 🔄 상태 관리

### status 필드 값
- `pending`: 등록 대기 중 (기본값)
- `approved`: 승인됨 (활동 가능)
- `rejected`: 거부됨

### 경력 선택 옵션
- "1년 미만"
- "1~3년"
- "3~5년"
- "5~10년"
- "10년 이상"

### 자격증 선택 옵션
- "요양보호사"
- "간호사"
- "간호조무사"
- "물리치료사"
- "사회복지사"
- "기타"

### 가능 지역 선택 옵션 (전국 17개 시/도)
- 수도권: "서울", "경기", "인천"
- 광역시: "부산", "대구", "대전", "광주", "울산", "세종"
- 도: "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"

## 🎨 UI 개선 사항 (2026-01-22 업데이트)

### 프로필 사진
- 원형 이미지 선택 UI
- 갤러리에서 선택
- 200x200px로 자동 리사이징
- Base64로 인코딩하여 Firestore에 저장

### 성별 선택
- 남성/여성 버튼 (OutlinedButton)
- 선택된 버튼 강조 표시

### 경력 선택
- ExposedDropdownMenuBox
- 5개 범위 옵션

### 자격증 선택
- FilterChip (다중 선택)
- 6개 옵션
- 선택된 항목 체크 아이콘 표시

### 가능 지역 선택
- FilterChip (다중 선택)
- 17개 시/도 옵션
- 선택된 항목 체크 아이콘 표시

## 🔮 향후 확장 가능 필드
- `rating`: 평점 (1-5)
- `reviewCount`: 리뷰 개수
- `matchCount`: 매칭 완료 횟수
- `updatedAt`: 마지막 수정 일시
- `desiredSalary`: 희망 급여
- `introduction`: 자기소개
- `age`: 나이
- `profileImageUrl`: Firebase Storage URL (Base64 대신)

---

**마지막 업데이트**: 2026-01-22
