# Match (매칭)

## 📍 Firestore 경로
```
matches/{documentId}
```

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `id` | string | ✅ | 문서 ID (자동 생성, @Exclude) | "match123..." |
| `serialNumber` | number | ✅ | 매칭 일련번호 | 30000000001 |
| `careRequestSerialNumber` | number | ✅ | 간병 신청 일련번호 | 10000000001 |
| `caregiverSerialNumber` | number | ✅ | 간병사 일련번호 | 20000000001 |
| `status` | string | ✅ | 매칭 상태 | "pending" (기본값) |
| `notes` | string | ✅ | 관리자 메모 | "지역 일치, 경력 우수" |
| `createdAt` | Timestamp | ✅ | 생성 일시 | 2026-01-23 17:00:00 |
| `updatedAt` | Timestamp | ✅ | 수정 일시 | 2026-01-23 17:00:00 |

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude

data class Match(
    @get:Exclude val id: String = "",
    val serialNumber: Long = 0,
    val careRequestSerialNumber: Long = 0,
    val caregiverSerialNumber: Long = 0,
    val status: String = "pending",
    val notes: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
```

## 📝 예시 데이터

```json
{
  "serialNumber": 30000000001,
  "careRequestSerialNumber": 10000000001,
  "caregiverSerialNumber": 20000000001,
  "status": "pending",
  "notes": "지역 일치, 경력 우수",
  "createdAt": {
    "_seconds": 1737619200,
    "_nanoseconds": 0
  },
  "updatedAt": {
    "_seconds": 1737619200,
    "_nanoseconds": 0
  }
}
```

## 📌 설계 특징

### serialNumber 방식 사용
- **Firestore 문서 ID 대신 serialNumber 사용**
- 간병 신청과 간병사를 serialNumber로 참조
- 사람이 읽기 쉽고 Firebase Console에서 검색 용이

### 장점
- ✅ 관리자가 "신청 100-0000-0001과 간병사 200-0000-0001을 매칭했습니다" 같은 표현 가능
- ✅ Firebase Console에서 `careRequestSerialNumber == 10000000001`로 쉽게 검색
- ✅ UI에서 포맷팅하여 표시: "매칭 #300-0000-0001"

### serialNumber 범위
- 매칭: 30000000001 ~ 39999999999
- 화면 표시: "300-0000-0001" 형식

## 🔄 상태 관리

### status 필드 값
- `pending`: 매칭 대기 중 (기본값)
- `confirmed`: 매칭 확정
- `completed`: 간병 완료
- `cancelled`: 매칭 취소

## 🔗 관련 데이터 업데이트

### 매칭 생성 시 자동 처리
1. **Match 문서 생성** (serialNumber 자동 생성)
2. **CareRequest 상태 업데이트**: `status` → "matched"

### 데이터 흐름
```
1. 관리자가 매칭 생성
   ↓
2. MatchRepository.generateSerialNumber() 호출
   → 30000000001 생성
   ↓
3. Match 문서 생성
   - serialNumber: 30000000001
   - careRequestSerialNumber: 10000000001
   - caregiverSerialNumber: 20000000001
   ↓
4. CareRequestRepository.updateCareRequestStatus() 호출
   → status: "pending" → "matched"
```

## 🔍 쿼리 예시

### Kotlin (앱)
```kotlin
// 모든 매칭 조회
firestore.collection("matches")
    .orderBy("createdAt", Query.Direction.DESCENDING)
    .get()
    .await()

// 특정 간병 신청의 매칭 조회
firestore.collection("matches")
    .whereEqualTo("careRequestSerialNumber", 10000000001)
    .get()
    .await()

// 특정 간병사의 매칭 조회
firestore.collection("matches")
    .whereEqualTo("caregiverSerialNumber", 20000000001)
    .get()
    .await()

// serialNumber로 검색
firestore.collection("matches")
    .whereEqualTo("serialNumber", 30000000001)
    .get()
    .await()
```

## 🎨 UI 표시 예시

### SerialNumberFormatter 사용
```kotlin
import com.ezlevup.ganbyeong24.util.SerialNumberFormatter

// 매칭 번호 표시
SerialNumberFormatter.formatMatch(30000000001)  
// → "매칭 #300-0000-0001"

// 간병 신청 번호 표시
SerialNumberFormatter.formatCareRequest(10000000001)  
// → "간병 신청 #100-0000-0001"

// 간병사 번호 표시
SerialNumberFormatter.formatCaregiver(20000000001)  
// → "간병사 #200-0000-0001"
```

## 🔮 향후 확장 가능 필드
- `matchedBy`: 매칭을 생성한 관리자 ID
- `confirmedAt`: 확정 일시
- `completedAt`: 완료 일시
- `cancelledAt`: 취소 일시
- `cancelReason`: 취소 사유
- `rating`: 평가 점수
- `review`: 리뷰 내용

---

**마지막 업데이트**: 2026-01-23
