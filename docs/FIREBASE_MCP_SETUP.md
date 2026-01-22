# Firebase MCP 설정 가이드

## 🔧 설정 방법

### 1. MCP 설정 파일 생성

예시 파일을 복사하여 실제 설정 파일을 생성하세요:

```bash
# PowerShell
Copy-Item .mcp-config.example.json .mcp-config.json
```

### 2. 서비스 계정 키 파일 준비

Firebase Console에서 서비스 계정 키를 다운로드하고 프로젝트 루트에 `firebase-service-account.json`으로 저장하세요.

**다운로드 방법:**
1. [Firebase Console](https://console.firebase.google.com/) 접속
2. Ganbyeong24 프로젝트 선택
3. ⚙️ 프로젝트 설정 → 서비스 계정 탭
4. "새 비공개 키 생성" 클릭
5. 다운로드한 JSON 파일을 `firebase-service-account.json`으로 이름 변경

### 3. 환경 변수 설정 (선택사항)

`.env` 파일이 자동으로 생성되어 있습니다. 필요시 수정하세요:

```env
GOOGLE_APPLICATION_CREDENTIALS=firebase-service-account.json
FIREBASE_PROJECT_ID=ganbyeong24
```

## 📁 파일 구조

```
Ganbyeong24/
├── .mcp-config.json              # 실제 설정 (Git 제외)
├── .mcp-config.example.json      # 설정 예시 (Git 포함)
├── firebase-service-account.json # 서비스 계정 키 (Git 제외)
├── .env                          # 환경 변수 (Git 제외)
└── .gitignore                    # 보안 파일 제외 설정
```

## 🔒 보안 주의사항

다음 파일들은 **절대 Git에 커밋하지 마세요**:
- ❌ `firebase-service-account.json`
- ❌ `.mcp-config.json`
- ❌ `.env`

이 파일들은 이미 `.gitignore`에 등록되어 있습니다.

## ✅ 설정 확인

Firebase MCP가 제대로 설정되었는지 확인하려면:

```
"Firebase MCP 서버가 연결되었는지 확인해줘"
"Firestore 컬렉션 목록을 보여줘"
```

## 🆘 문제 해결

### "서비스 계정 키를 찾을 수 없습니다" 오류
- `firebase-service-account.json` 파일이 프로젝트 루트에 있는지 확인
- 파일 이름이 정확한지 확인

### "권한 부족" 오류
- Firebase Console → IAM 및 관리자에서 서비스 계정 권한 확인
- 필요한 역할: Firebase Admin SDK Administrator

### MCP 서버 연결 실패
- Gemini CLI 재시작
- `.mcp-config.json` 파일 형식 확인 (JSON 유효성)
