# Firebase 설정 가이드

## 📋 개요

이 문서는 간병24 웹 플랫폼에서 Firebase를 설정하는 방법을 안내합니다.

## 🔥 Firebase 프로젝트 정보

### 프로젝트 기본 정보
- **프로젝트 이름**: Ganbyeong24
- **프로젝트 ID**: `ganbyeong24` (실제 ID로 변경 필요)
- **지역**: asia-northeast3 (서울)

### 사용 중인 Firebase 서비스
- ✅ **Authentication** (이메일/비밀번호)
- ✅ **Firestore Database**
- ⚠️ **Cloud Functions** (필요 시)
- ⚠️ **Hosting** (선택)

---

## 🌐 웹 앱 Firebase 설정

### 1. Firebase Console에서 웹 앱 추가

1. [Firebase Console](https://console.firebase.google.com/) 접속
2. Ganbyeong24 프로젝트 선택
3. 프로젝트 설정 → 일반 → "앱 추가" → 웹 (</>) 선택
4. 앱 닉네임 입력: `Ganbyeong24 Web`
5. Firebase Hosting 설정 (선택)
6. **Firebase SDK 구성 코드 복사**

### 2. Firebase Config 정보

Firebase Console에서 제공하는 설정 정보 예시:

```javascript
const firebaseConfig = {
  apiKey: "AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  authDomain: "ganbyeong24.firebaseapp.com",
  projectId: "ganbyeong24",
  storageBucket: "ganbyeong24.appspot.com",
  messagingSenderId: "123456789012",
  appId: "1:123456789012:web:abcdef1234567890"
};
```

> ⚠️ **주의**: 실제 값은 Firebase Console에서 확인하세요!

---

## 🔐 환경 변수 설정

### React + Vite 프로젝트

#### 1. `.env` 파일 생성

프로젝트 루트에 `.env` 파일 생성:

```env
# Firebase Configuration
VITE_FIREBASE_API_KEY=AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
VITE_FIREBASE_AUTH_DOMAIN=ganbyeong24.firebaseapp.com
VITE_FIREBASE_PROJECT_ID=ganbyeong24
VITE_FIREBASE_STORAGE_BUCKET=ganbyeong24.appspot.com
VITE_FIREBASE_MESSAGING_SENDER_ID=123456789012
VITE_FIREBASE_APP_ID=1:123456789012:web:abcdef1234567890
```

#### 2. `.env.example` 파일 생성 (Git에 포함)

```env
# Firebase Configuration (Replace with your actual values)
VITE_FIREBASE_API_KEY=your_api_key_here
VITE_FIREBASE_AUTH_DOMAIN=your_project.firebaseapp.com
VITE_FIREBASE_PROJECT_ID=your_project_id
VITE_FIREBASE_STORAGE_BUCKET=your_project.appspot.com
VITE_FIREBASE_MESSAGING_SENDER_ID=your_sender_id
VITE_FIREBASE_APP_ID=your_app_id
```

#### 3. `.gitignore` 확인

`.env` 파일이 Git에 커밋되지 않도록 확인:

```gitignore
# Environment variables
.env
.env.local
.env.production
```

---

## 🛠 Firebase 초기화 코드

### `src/lib/firebase.ts`

```typescript
import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';

// Firebase 설정
const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID,
};

// Firebase 초기화
const app = initializeApp(firebaseConfig);

// Firebase 서비스
export const auth = getAuth(app);
export const db = getFirestore(app);

export default app;
```

### 사용 예시

```typescript
import { auth, db } from '@/lib/firebase';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { collection, getDocs } from 'firebase/firestore';

// Authentication
const login = async (email: string, password: string) => {
  const userCredential = await signInWithEmailAndPassword(auth, email, password);
  return userCredential.user;
};

// Firestore
const getCareRequests = async () => {
  const snapshot = await getDocs(collection(db, 'care_requests'));
  return snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
};
```

---

## 🔒 Firestore Security Rules

### 현재 규칙 (테스트용)

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    // 2026년 2월 13일까지 모든 읽기/쓰기 허용 (테스트용)
    match /{document=**} {
      allow read, write: if request.time < timestamp.date(2026, 2, 13);
    }
  }
}
```

### 프로덕션 권장 규칙

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    
    // 사용자 정보
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    
    // 간병 신청
    match /care_requests/{requestId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
                               resource.data.userId == request.auth.uid;
    }
    
    // 간병사 등록
    match /caregivers/{caregiverId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
                               resource.data.userId == request.auth.uid;
    }
  }
}
```

### 규칙 배포 방법

1. Firebase Console → Firestore Database → 규칙 탭
2. 위 규칙 복사 & 붙여넣기
3. "게시" 클릭

---

## 👤 Authentication 설정

### 1. 이메일/비밀번호 인증 활성화

1. Firebase Console → Authentication → Sign-in method
2. "이메일/비밀번호" 클릭
3. "사용 설정" 토글 ON
4. 저장

### 2. 승인된 도메인 추가

**개발 환경:**
- `localhost`

**프로덕션 환경:**
- `ganbyeong24.com` (실제 도메인)
- `ganbyeong24-web.vercel.app` (Vercel 배포 시)

**설정 방법:**
1. Firebase Console → Authentication → Settings → Authorized domains
2. "도메인 추가" 클릭
3. 도메인 입력 후 추가

---

## 📦 필수 패키지 설치

```bash
npm install firebase
```

**버전 확인:**
- `firebase`: ^10.x.x (최신 버전 권장)

---

## 🚀 배포 환경별 설정

### 개발 환경 (localhost)

`.env` 파일 사용 (위 참조)

### 프로덕션 환경 (Vercel)

**Vercel Dashboard에서 환경 변수 설정:**

1. Vercel 프로젝트 → Settings → Environment Variables
2. 다음 변수들 추가:
   - `VITE_FIREBASE_API_KEY`
   - `VITE_FIREBASE_AUTH_DOMAIN`
   - `VITE_FIREBASE_PROJECT_ID`
   - `VITE_FIREBASE_STORAGE_BUCKET`
   - `VITE_FIREBASE_MESSAGING_SENDER_ID`
   - `VITE_FIREBASE_APP_ID`

---

## ✅ 설정 확인 체크리스트

### Firebase Console
- [ ] 웹 앱 추가 완료
- [ ] Firebase Config 정보 복사
- [ ] Firestore Database 생성
- [ ] Authentication 이메일/비밀번호 활성화
- [ ] 승인된 도메인 추가
- [ ] Security Rules 설정

### 웹 프로젝트
- [ ] `.env` 파일 생성
- [ ] `.env.example` 파일 생성
- [ ] `.gitignore`에 `.env` 추가
- [ ] `firebase` 패키지 설치
- [ ] `src/lib/firebase.ts` 파일 생성
- [ ] Firebase 초기화 테스트

---

## 🧪 연결 테스트

### 간단한 테스트 코드

```typescript
// src/App.tsx 또는 테스트 파일
import { auth, db } from './lib/firebase';
import { collection, getDocs } from 'firebase/firestore';

const testFirebaseConnection = async () => {
  try {
    console.log('Firebase Auth:', auth.currentUser);
    console.log('Firebase Firestore:', db);
    
    // Firestore 연결 테스트
    const snapshot = await getDocs(collection(db, 'users'));
    console.log('Users count:', snapshot.size);
    
    console.log('✅ Firebase 연결 성공!');
  } catch (error) {
    console.error('❌ Firebase 연결 실패:', error);
  }
};

// 컴포넌트 마운트 시 실행
useEffect(() => {
  testFirebaseConnection();
}, []);
```

---

## 🔍 문제 해결

### API Key 오류
- `.env` 파일의 변수명이 `VITE_` 접두사로 시작하는지 확인
- 개발 서버 재시작 (`npm run dev`)

### CORS 오류
- Firebase Console에서 승인된 도메인 확인
- `localhost` 또는 배포 도메인이 추가되어 있는지 확인

### Firestore 권한 오류
- Security Rules 확인
- 로그인 상태 확인 (`auth.currentUser`)

---

## 📞 참고 자료

- [Firebase 공식 문서](https://firebase.google.com/docs)
- [Firebase JavaScript SDK](https://firebase.google.com/docs/web/setup)
- [Vite 환경 변수](https://vitejs.dev/guide/env-and-mode.html)
