# Caregiver (간병사 등록)

## 📍 Firestore 경로
```
caregivers/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `id` | string | ✅ | 문서 ID (자동 생성, @Exclude) | "xyz789..." |
| `serialNumber` | number | ✅ | 일련번호 (사람이 읽기 쉬운) | 20000000001 |
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

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Caregiver(
    @get:Exclude val id: String = "",
    val serialNumber: Long = 0,
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
  "serialNumber": 20000000001,
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

## 📌 주요 변경사항 (2026-01-23)

### serialNumber 필드 추가
- **타입**: Long (11자리 숫자)
- **범위**: 20000000001 ~ 29999999999
- **화면 표시**: "200-0000-0001" 형식 (SerialNumberFormatter 사용)
- **목적**: 사람이 읽기 쉬운 번호, Firebase Console 검색 용이
- **생성**: Firestore Transaction으로 중복 방지

### id 필드 @Exclude
- Firestore 저장 시 제외 (`@get:Exclude`)
- 조회 시 문서 ID를 `id` 필드에 할당

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

## 🔍 쿼리 예시

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

// serialNumber로 검색
firestore.collection("caregivers")
    .whereEqualTo("serialNumber", 20000000001)
    .get()
    .await()
```

---

**마지막 업데이트**: 2026-01-23
