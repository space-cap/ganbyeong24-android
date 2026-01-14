# 간병24 - 개발 가이드 (Development Guide)

## 📋 문서 정보

- **프로젝트명**: 간병24
- **작성일**: 2026-01-14
- **버전**: 1.0
- **작성자**: Development Team

---

## 📖 목차

1. [코딩 컨벤션](#-코딩-컨벤션)
2. [Git 브랜치 전략](#-git-브랜치-전략)
3. [커밋 메시지 규칙](#-커밋-메시지-규칙)
4. [코드 리뷰 가이드](#-코드-리뷰-가이드)
5. [테스트 가이드](#-테스트-가이드)
6. [문서화 가이드](#-문서화-가이드)

---

## 📝 코딩 컨벤션

### Kotlin 코딩 스타일

**공식 가이드 준수**: [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)

### 네이밍 규칙

#### 1. 패키지명
- **소문자만 사용**, 언더스코어 없음
- **복수형 사용**

```kotlin
✅ Good
package com.ganbyeong24.app.presentation.screens
package com.ganbyeong24.app.data.repositories

❌ Bad
package com.ganbyeong24.app.presentation.Screen
package com.ganbyeong24.app.data.repository
```

#### 2. 클래스명
- **PascalCase** 사용
- 명사 또는 명사구

```kotlin
✅ Good
class CareRequestViewModel
class FirebaseDataSource
data class CareRequest

❌ Bad
class careRequestViewModel
class firebase_data_source
```

#### 3. 함수명
- **camelCase** 사용
- 동사로 시작

```kotlin
✅ Good
fun submitCareRequest()
fun validatePhoneNumber()
fun onNameChange()

❌ Bad
fun SubmitCareRequest()
fun validate_phone_number()
fun nameChange()
```

#### 4. 변수명
- **camelCase** 사용
- 의미 있는 이름 사용

```kotlin
✅ Good
val patientName: String
val isLoading: Boolean
private val _state = MutableStateFlow()

❌ Bad
val pn: String
val loading: Boolean
val state = MutableStateFlow()
```

#### 5. 상수명
- **UPPER_SNAKE_CASE** 사용
- `const val` 또는 `companion object` 내부

```kotlin
✅ Good
const val MAX_NAME_LENGTH = 50
const val PHONE_NUMBER_PATTERN = "^010\\d{8}$"

companion object {
    const val TAG = "CareRequestViewModel"
}

❌ Bad
const val maxNameLength = 50
val PHONE_NUMBER_PATTERN = "^010\\d{8}$"
```

### Composable 함수 규칙

#### 1. 네이밍
- **PascalCase** 사용
- 명사 또는 형용사+명사

```kotlin
✅ Good
@Composable
fun CareRequestScreen()

@Composable
fun GanbyeongButton()

@Composable
fun LoadingDialog()

❌ Bad
@Composable
fun careRequestScreen()

@Composable
fun button()
```

#### 2. 파라미터 순서
1. 필수 파라미터
2. `Modifier` (항상 `Modifier = Modifier` 기본값)
3. 선택적 파라미터
4. 람다 (마지막)

```kotlin
✅ Good
@Composable
fun GanbyeongButton(
    text: String,                    // 필수
    onClick: () -> Unit,             // 필수
    modifier: Modifier = Modifier,   // Modifier
    enabled: Boolean = true,         // 선택
    isLoading: Boolean = false       // 선택
)

❌ Bad
@Composable
fun GanbyeongButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
)
```

#### 3. Preview 작성
- 모든 주요 Composable은 `@Preview` 제공

```kotlin
@Preview(showBackground = true)
@Composable
private fun GanbyeongButtonPreview() {
    GanbyeongTheme {
        GanbyeongButton(
            text = "신청하기",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GanbyeongButtonLoadingPreview() {
    GanbyeongTheme {
        GanbyeongButton(
            text = "신청하기",
            onClick = {},
            isLoading = true
        )
    }
}
```

### 파일 구조

#### 1. 파일당 하나의 public 클래스/함수
```kotlin
✅ Good
// CareRequestViewModel.kt
class CareRequestViewModel : ViewModel() { ... }

// CareRequestScreen.kt
@Composable
fun CareRequestScreen() { ... }

❌ Bad
// CareRequest.kt
class CareRequestViewModel : ViewModel() { ... }
@Composable
fun CareRequestScreen() { ... }
data class CareRequestState() { ... }
```

#### 2. Import 정리
- Android Studio의 "Optimize Imports" 사용
- 와일드카드 import 최소화

```kotlin
✅ Good
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button

❌ Bad
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
```

### 주석 규칙

#### 1. KDoc 주석
- Public API에는 KDoc 작성

```kotlin
/**
 * 간병 신청을 처리하는 ViewModel
 *
 * @property repository 간병 신청 데이터를 관리하는 Repository
 */
class CareRequestViewModel(
    private val repository: CareRequestRepository
) : ViewModel() {
    
    /**
     * 간병 신청을 제출합니다.
     * 
     * 유효성 검사를 수행한 후 Firebase에 데이터를 저장합니다.
     */
    fun submitCareRequest() { ... }
}
```

#### 2. 인라인 주석
- 복잡한 로직에만 사용
- "무엇을"보다 "왜"를 설명

```kotlin
✅ Good
// 전화번호 형식: 010으로 시작하는 11자리 숫자
val phoneRegex = "^010\\d{8}$".toRegex()

// Firebase 타임스탬프는 밀리초 단위이므로 변환 필요
val timestamp = Timestamp(dateInMillis)

❌ Bad
// 변수 선언
val name = state.patientName

// 버튼 클릭
onClick = { submitCareRequest() }
```

### 코드 포맷팅

#### 1. 들여쓰기
- **4 spaces** (탭 아님)

#### 2. 줄 길이
- **최대 120자**
- 긴 경우 적절히 줄바꿈

```kotlin
✅ Good
fun validatePhoneNumber(
    phoneNumber: String,
    showError: Boolean = true
): Boolean {
    return phoneRegex.matches(phoneNumber)
}

❌ Bad
fun validatePhoneNumber(phoneNumber: String, showError: Boolean = true): Boolean {
    return phoneRegex.matches(phoneNumber)
}
```

#### 3. 빈 줄
- 논리적 블록 사이에 빈 줄 추가

```kotlin
✅ Good
class CareRequestViewModel {
    private val _state = MutableStateFlow(CareRequestState())
    val state = _state.asStateFlow()
    
    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }
    
    fun submitRequest() {
        // ...
    }
}

❌ Bad
class CareRequestViewModel {
    private val _state = MutableStateFlow(CareRequestState())
    val state = _state.asStateFlow()
    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }
    fun submitRequest() {
        // ...
    }
}
```

---

## 🌿 Git 브랜치 전략

### Git Flow 전략 사용

```
main (프로덕션)
  ↑
develop (개발)
  ↑
feature/* (기능 개발)
hotfix/* (긴급 수정)
release/* (릴리스 준비)
```

### 브랜치 종류

#### 1. `main` 브랜치
- **프로덕션 배포용**
- 항상 안정적인 상태 유지
- 직접 커밋 금지
- `develop` 또는 `hotfix`에서만 머지

#### 2. `develop` 브랜치
- **개발 통합 브랜치**
- 다음 릴리스 준비
- `feature` 브랜치들이 머지되는 곳

#### 3. `feature/*` 브랜치
- **새 기능 개발**
- `develop`에서 분기
- 완료 후 `develop`으로 머지

**네이밍 규칙**:
```
feature/care-request-screen
feature/firebase-integration
feature/koin-setup
```

#### 4. `hotfix/*` 브랜치
- **긴급 버그 수정**
- `main`에서 분기
- 완료 후 `main`과 `develop` 모두에 머지

**네이밍 규칙**:
```
hotfix/fix-phone-validation
hotfix/crash-on-submit
```

#### 5. `release/*` 브랜치
- **릴리스 준비**
- `develop`에서 분기
- 버그 수정만 허용
- 완료 후 `main`과 `develop`에 머지

**네이밍 규칙**:
```
release/1.0.0
release/1.1.0
```

### 브랜치 작업 흐름

#### 새 기능 개발

```bash
# 1. develop에서 최신 코드 받기
git checkout develop
git pull origin develop

# 2. feature 브랜치 생성
git checkout -b feature/care-request-screen

# 3. 작업 및 커밋
git add .
git commit -m "feat: Add CareRequestScreen UI"

# 4. 원격에 푸시
git push origin feature/care-request-screen

# 5. Pull Request 생성 (GitHub/GitLab)
# develop <- feature/care-request-screen

# 6. 리뷰 후 머지
# 7. 로컬 브랜치 삭제
git checkout develop
git branch -d feature/care-request-screen
```

#### 긴급 수정

```bash
# 1. main에서 hotfix 브랜치 생성
git checkout main
git checkout -b hotfix/fix-phone-validation

# 2. 수정 및 커밋
git add .
git commit -m "fix: Fix phone number validation regex"

# 3. main에 머지
git checkout main
git merge hotfix/fix-phone-validation
git tag -a v1.0.1 -m "Hotfix: Phone validation"
git push origin main --tags

# 4. develop에도 머지
git checkout develop
git merge hotfix/fix-phone-validation
git push origin develop

# 5. hotfix 브랜치 삭제
git branch -d hotfix/fix-phone-validation
```

---

## 💬 커밋 메시지 규칙

### Conventional Commits 사용

**형식**:
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Type 종류

| Type | 설명 | 예시 |
|------|------|------|
| `feat` | 새 기능 추가 | `feat: Add CareRequestScreen` |
| `fix` | 버그 수정 | `fix: Fix phone validation` |
| `docs` | 문서 변경 | `docs: Update README` |
| `style` | 코드 포맷팅 (기능 변경 없음) | `style: Format code` |
| `refactor` | 리팩토링 | `refactor: Simplify validation logic` |
| `test` | 테스트 추가/수정 | `test: Add ViewModel tests` |
| `chore` | 빌드/설정 변경 | `chore: Update dependencies` |
| `perf` | 성능 개선 | `perf: Optimize image loading` |

### Scope (선택 사항)

프로젝트의 어느 부분이 변경되었는지 명시:

```
feat(ui): Add GanbyeongButton component
fix(viewmodel): Fix state update issue
docs(readme): Add setup instructions
```

### Subject

- **현재 시제** 사용 ("Added" ❌, "Add" ✅)
- **첫 글자 대문자**
- **마침표 없음**
- **50자 이내**

```
✅ Good
feat: Add CareRequestScreen
fix: Fix phone number validation
docs: Update PRD document

❌ Bad
feat: added care request screen.
fix: fixed the phone number validation bug
docs: updated the prd document
```

### Body (선택 사항)

- 변경 이유와 방법 설명
- 72자마다 줄바꿈

```
feat: Add phone number auto-formatting

Automatically format phone numbers as user types
to improve UX and reduce validation errors.

- Add formatPhoneNumber utility function
- Update GanbyeongTextField to use formatter
- Add tests for phone formatting
```

### Footer (선택 사항)

- Breaking changes
- 이슈 참조

```
feat: Change API response format

BREAKING CHANGE: CareRequest now returns Timestamp instead of Long

Closes #123
Refs #456
```

### 커밋 메시지 예시

#### 1. 간단한 커밋
```bash
git commit -m "feat: Add SplashScreen"
git commit -m "fix: Fix navigation bug"
git commit -m "docs: Add technical design document"
```

#### 2. 상세한 커밋
```bash
git commit -m "feat(ui): Add CareRequestScreen with validation

Implement the care request form with the following features:
- Patient and guardian name inputs
- Phone number validation
- Date picker for care period
- Location input

All fields have proper error handling and validation."
```

#### 3. Breaking Change
```bash
git commit -m "refactor!: Change state management to StateFlow

BREAKING CHANGE: ViewModel now uses StateFlow instead of LiveData

Migration guide:
- Replace observe() with collectAsState()
- Update all ViewModels to use StateFlow"
```

---

## 👀 코드 리뷰 가이드

### 리뷰어 체크리스트

#### 1. 기능성
- [ ] 요구사항을 충족하는가?
- [ ] 엣지 케이스를 처리하는가?
- [ ] 에러 핸들링이 적절한가?

#### 2. 코드 품질
- [ ] 코딩 컨벤션을 따르는가?
- [ ] 중복 코드가 없는가?
- [ ] 함수/클래스가 단일 책임을 가지는가?

#### 3. 성능
- [ ] 불필요한 recomposition이 없는가?
- [ ] 메모리 누수 가능성이 없는가?
- [ ] 비동기 처리가 적절한가?

#### 4. 테스트
- [ ] 테스트 코드가 포함되어 있는가?
- [ ] 테스트가 통과하는가?

#### 5. 문서화
- [ ] 주석이 적절한가?
- [ ] README/문서 업데이트가 필요한가?

### Pull Request 템플릿

```markdown
## 변경 사항
<!-- 무엇을 변경했는지 간단히 설명 -->

## 변경 이유
<!-- 왜 이 변경이 필요한지 설명 -->

## 스크린샷 (UI 변경 시)
<!-- Before/After 스크린샷 -->

## 테스트
- [ ] 단위 테스트 추가/업데이트
- [ ] UI 테스트 추가/업데이트
- [ ] 수동 테스트 완료

## 체크리스트
- [ ] 코딩 컨벤션 준수
- [ ] 빌드 성공
- [ ] 테스트 통과
- [ ] 문서 업데이트 (필요 시)

## 관련 이슈
Closes #123
```

### 리뷰 코멘트 가이드

#### 1. 건설적인 피드백
```
✅ Good
"이 함수가 너무 길어 보입니다. 
validateForm()을 여러 개의 작은 함수로 분리하는 것은 어떨까요?
예: validateName(), validatePhone() 등"

❌ Bad
"이 코드는 너무 복잡합니다."
```

#### 2. 질문하기
```
✅ Good
"여기서 null 체크가 필요한 이유가 있나요?
위에서 이미 검증했다면 !! 연산자를 사용해도 될 것 같습니다."

❌ Bad
"왜 이렇게 했나요?"
```

#### 3. 칭찬하기
```
✅ Good
"이 유틸리티 함수 정말 깔끔하네요! 
다른 곳에서도 재사용할 수 있을 것 같습니다."
```

### 리뷰 우선순위

#### P0 (필수 수정)
- 버그
- 보안 이슈
- 성능 문제

#### P1 (권장 수정)
- 코드 품질 개선
- 리팩토링 제안

#### P2 (선택 사항)
- 네이밍 제안
- 스타일 개선

---

## 🧪 테스트 가이드

### 테스트 파일 구조

```
app/src/
├── main/
│   └── java/com/ganbyeong24/app/
└── test/
    └── java/com/ganbyeong24/app/
        ├── data/
        │   └── repository/
        │       └── CareRequestRepositoryTest.kt
        ├── presentation/
        │   └── screens/
        │       └── care_request/
        │           └── CareRequestViewModelTest.kt
        └── util/
            └── ValidationUtilsTest.kt
```

### 단위 테스트 작성

#### ViewModel 테스트 예시

```kotlin
class CareRequestViewModelTest {
    
    private lateinit var viewModel: CareRequestViewModel
    private lateinit var repository: CareRequestRepository
    
    @Before
    fun setup() {
        repository = mockk()
        viewModel = CareRequestViewModel(repository)
    }
    
    @Test
    fun `환자명 입력 시 상태가 업데이트된다`() = runTest {
        // Given
        val name = "홍길동"
        
        // When
        viewModel.onPatientNameChange(name)
        
        // Then
        assertEquals(name, viewModel.state.value.patientName)
        assertNull(viewModel.state.value.patientNameError)
    }
    
    @Test
    fun `유효하지 않은 전화번호는 에러를 표시한다`() = runTest {
        // Given
        viewModel.onGuardianPhoneNumberChange("123")
        
        // When
        viewModel.submitCareRequest()
        
        // Then
        assertNotNull(viewModel.state.value.guardianPhoneNumberError)
        assertFalse(viewModel.state.value.isLoading)
    }
    
    @Test
    fun `신청 성공 시 isSuccess가 true가 된다`() = runTest {
        // Given
        setupValidForm()
        coEvery { repository.saveCareRequest(any()) } returns Result.success(Unit)
        
        // When
        viewModel.submitCareRequest()
        
        // Then
        assertTrue(viewModel.state.value.isSuccess)
        assertFalse(viewModel.state.value.isLoading)
    }
}
```

### 테스트 네이밍

```kotlin
✅ Good (한글)
@Test
fun `환자명이 2자 미만이면 에러를 표시한다`()

@Test
fun `전화번호 형식이 올바르면 유효성 검사를 통과한다`()

✅ Good (영문)
@Test
fun `should show error when patient name is less than 2 characters`()

@Test
fun `should pass validation when phone number format is correct`()

❌ Bad
@Test
fun test1()

@Test
fun testPhoneValidation()
```

### 테스트 커버리지 목표

- **ViewModel**: 80% 이상
- **Repository**: 70% 이상
- **Utility**: 90% 이상

---

## 📚 문서화 가이드

### README.md 구조

```markdown
# 간병24

간병인 매칭 플랫폼 Android 앱

## 📱 주요 기능
- 간병 서비스 신청
- 간병사 등록
- 전화 상담 연결

## 🛠 기술 스택
- Kotlin
- Jetpack Compose
- Firebase Firestore
- Koin

## 🚀 시작하기

### 요구사항
- Android Studio Hedgehog 이상
- JDK 17
- Android SDK 24 이상

### 설치
1. 저장소 클론
2. Firebase 설정
3. 빌드 및 실행

## 📖 문서
- [PRD](docs/PRD.md)
- [기술 설계서](docs/TechnicalDesign.md)
- [화면 설계서](docs/ScreenDesign.md)

## 🤝 기여하기
[개발 가이드](docs/DevelopmentGuide.md) 참조

## 📄 라이선스
MIT License
```

### 코드 문서화

#### 1. 파일 헤더 (선택 사항)
```kotlin
/**
 * 간병 신청 화면
 * 
 * 보호자가 환자 정보와 간병 요구사항을 입력하여
 * 간병 서비스를 신청하는 화면입니다.
 * 
 * @author Development Team
 * @since 1.0.0
 */
```

#### 2. 복잡한 로직 설명
```kotlin
/**
 * 전화번호 유효성 검사
 * 
 * 한국 휴대폰 번호 형식(010-XXXX-XXXX)을 검증합니다.
 * 하이픈은 있어도 되고 없어도 됩니다.
 * 
 * @param phoneNumber 검증할 전화번호
 * @return 유효하면 true, 아니면 false
 */
fun validatePhoneNumber(phoneNumber: String): Boolean {
    val cleanNumber = phoneNumber.replace("-", "")
    return phoneRegex.matches(cleanNumber)
}
```

---

## 🔧 개발 환경 설정

### Android Studio 설정

#### 1. Code Style
`Settings` → `Editor` → `Code Style` → `Kotlin`
- "Set from..." → "Kotlin style guide"

#### 2. Inspections
`Settings` → `Editor` → Inspections`
- Kotlin 관련 검사 활성화

#### 3. Plugins 추천
- **Kotlin** (기본 포함)
- **Compose Multiplatform IDE Support**
- **GitToolBox**
- **Rainbow Brackets**

### Git Hooks 설정

#### pre-commit hook
```bash
#!/bin/sh
# .git/hooks/pre-commit

# 빌드 확인
./gradlew assembleDebug

if [ $? -ne 0 ]; then
    echo "빌드 실패. 커밋을 중단합니다."
    exit 1
fi

# 테스트 실행
./gradlew test

if [ $? -ne 0 ]; then
    echo "테스트 실패. 커밋을 중단합니다."
    exit 1
fi
```

---

## 📊 성능 최적화 가이드

### Compose 최적화

#### 1. remember 사용
```kotlin
✅ Good
@Composable
fun MyScreen() {
    val scrollState = rememberScrollState()
    // ...
}

❌ Bad
@Composable
fun MyScreen() {
    val scrollState = ScrollState(0)
    // 매번 새로 생성됨
}
```

#### 2. derivedStateOf 사용
```kotlin
✅ Good
val isScrolled by remember {
    derivedStateOf { scrollState.value > 0 }
}

❌ Bad
val isScrolled = scrollState.value > 0
// scrollState 변경 시마다 recomposition
```

#### 3. key 사용
```kotlin
✅ Good
LazyColumn {
    items(items, key = { it.id }) { item ->
        ItemRow(item)
    }
}

❌ Bad
LazyColumn {
    items(items) { item ->
        ItemRow(item)
    }
}
```

---

## 🔄 버전 관리

| 버전 | 날짜 | 작성자 | 변경 사항 |
|------|------|--------|-----------|
| 1.0 | 2026-01-14 | Development Team | 초기 개발 가이드 작성 |

---

**문서 작성일**: 2026년 1월 14일  
**최종 수정일**: 2026년 1월 14일  
**문서 상태**: 초안 (Draft)
