# User (사용자 정보)

## 📍 Firestore 경로
```
users/{userId}
```

**중요**: `userId`는 Firebase Authentication의 UID와 동일합니다.

## 📊 필드 구조

| 필드명 | 타입 | 필수 | 설명 | 예시 |
|--------|------|------|------|------|
| `userId` | string | ✅ | Firebase Auth UID | "bBSZfTWfWROoOnngD5DV9S2s4tK2" |
| `email` | string | ✅ | 사용자 이메일 | "user@example.com" |
| `role` | string | ✅ | 사용자 역할 | "user" 또는 "admin" |
| `createdAt` | Timestamp | ✅ | 계정 생성 일시 | 2026-01-17 19:00:00 |
| `isDeleted` | boolean | ✅ | 삭제 여부 (Soft Delete) | false |
| `deletedAt` | Timestamp | ❌ | 삭제 일시 | null 또는 Timestamp |

## 💻 TypeScript 타입 정의

```typescript
import { Timestamp } from 'firebase/firestore';

interface User {
  userId: string;
  email: string;
  role: string;  // "user" | "admin"
  createdAt: Timestamp;
  isDeleted: boolean;
  deletedAt: Timestamp | null;
}
```

## 🤖 Kotlin 데이터 클래스

```kotlin
import com.google.firebase.Timestamp

data class User(
    val userId: String = "",
    val email: String = "",
    val role: String = "user",
    val createdAt: Timestamp = Timestamp.now(),
    val isDeleted: Boolean = false,
    val deletedAt: Timestamp? = null
)
```

## 📝 예시 데이터

### 정상 사용자
```json
{
  "userId": "bBSZfTWfWROoOnngD5DV9S2s4tK2",
  "email": "user@example.com",
  "role": "user",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  },
  "isDeleted": false,
  "deletedAt": null
}
```

### 탈퇴한 사용자 (Soft Delete)
```json
{
  "userId": "abc123def456ghi789",
  "email": "deleted@example.com",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  },
  "isDeleted": true,
  "deletedAt": {
    "_seconds": 1737201600,
    "_nanoseconds": 0
  }
}
```

### 관리자
```json
{
  "userId": "adminUserId123",
  "email": "admin@example.com",
  "role": "admin",
  "createdAt": {
    "_seconds": 1737115200,
    "_nanoseconds": 0
  },
  "isDeleted": false,
  "deletedAt": null
}
```

## 🔍 쿼리 예시

### TypeScript (웹)
```typescript
import { doc, getDoc } from 'firebase/firestore';

// 특정 사용자 정보 조회
const userRef = doc(db, 'users', userId);
const userSnap = await getDoc(userRef);

if (userSnap.exists()) {
  const user = userSnap.data() as User;
  
  // 탈퇴한 사용자 체크
  if (user.isDeleted) {
    console.log('탈퇴한 사용자입니다.');
  }
}
```

### Kotlin (앱)
```kotlin
// 특정 사용자 정보 조회
val userRef = firestore.collection("users").document(userId)
val snapshot = userRef.get().await()

val user = snapshot.toObject(User::class.java)
if (user?.isDeleted == true) {
    // 탈퇴한 사용자 처리
}
```

## 🔒 Soft Delete 방식

### 개념
사용자가 "회원 탈퇴"를 하면:
1. **Firebase Authentication 계정 삭제** (로그인 차단)
2. **Firestore User 문서는 유지** (`isDeleted = true` 설정)

### 장점
- ✅ 법적 요구사항 충족 (개인정보 보관 의무)
- ✅ 데이터 복구 가능
- ✅ 비즈니스 분석 가능 (탈퇴 사용자 통계)
- ✅ 관련 데이터 무결성 유지 (간병 신청 이력 등)

### 로그인 차단 로직

**앱 (LoginViewModel):**
```kotlin
// 로그인 성공 후
userRepository.getUser(userId).fold(
    onSuccess = { user ->
        if (user.isDeleted) {
            // 탈퇴한 계정
            authRepository.logout()
            _state.value = _state.value.copy(
                errorMessage = "탈퇴한 계정입니다"
            )
        } else {
            // 정상 로그인
            _state.value = _state.value.copy(isSuccess = true)
        }
    }
)
```

**웹:**
```typescript
// 로그인 성공 후
const userRef = doc(db, 'users', userId);
const userSnap = await getDoc(userRef);

if (userSnap.exists()) {
  const user = userSnap.data() as User;
  
  if (user.isDeleted) {
    // 탈퇴한 계정
    await signOut(auth);
    throw new Error('탈퇴한 계정입니다');
  }
}
```

## 📌 주의사항

1. **Document ID = Firebase Auth UID**: User 문서의 ID는 항상 Firebase Auth의 UID와 동일해야 합니다.
2. **회원가입 시 자동 생성**: 회원가입 성공 시 자동으로 User 문서를 생성해야 합니다.
3. **Hard Delete 금지**: 사용자 데이터는 절대 물리적으로 삭제하지 않습니다 (Soft Delete만 사용).
4. **Firebase Auth 삭제**: 회원 탈퇴 시 Firebase Auth 계정은 삭제하여 로그인을 차단합니다.
5. **role 필드**: 기본값은 `"user"`, 관리자는 Firebase Console에서 수동으로 `"admin"`으로 설정합니다.

## 🔐 관리자 권한 부여 방법

1. [Firebase Console](https://console.firebase.google.com/) 접속
2. Ganbyeong24 프로젝트 선택
3. **Firestore Database** → `users` 컨렉션
4. 해당 사용자 문서 선택
5. **필드 추가** 또는 **편집**:
   - 필드 이름: `role`
   - 필드 타입: `string`
   - 값: `admin`
6. 저장

## 🔄 생명주기

```
1. 회원가입
   ↓
   Firebase Auth 계정 생성
   ↓
   User 문서 생성 (isDeleted: false)
   
2. 정상 사용
   ↓
   로그인 시 isDeleted 체크
   
3. 회원 탈퇴
   ↓
   User 문서 업데이트 (isDeleted: true, deletedAt: now)
   ↓
   Firebase Auth 계정 삭제
   ↓
   로그인 불가
```
