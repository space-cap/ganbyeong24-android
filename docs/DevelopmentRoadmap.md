# 간병24 - 개발 시작 로드맵 (초보자용)

## 📋 문서 정보

- **프로젝트명**: 간병24
- **작성일**: 2026-01-14
- **대상**: Android 개발 초보자
- **예상 소요 기간**: 3-4주

---

## 🎯 로드맵 개요

이 문서는 간병24 앱을 처음부터 끝까지 개발하는 과정을 단계별로 안내합니다.
각 단계마다 **무엇을**, **왜**, **어떻게** 해야 하는지 자세히 설명합니다.

### 전체 일정

```
Week 1: 프로젝트 초기 설정 + 기반 구축
Week 2: 화면 개발 (1-3)
Week 3: 화면 개발 (4-5) + 데이터 레이어
Week 4: 테스트 및 버그 수정
```

---

## 📅 1단계: 프로젝트 초기 설정 (2-3일)

### 목표
- Firebase 프로젝트 생성 및 연동
- 필요한 라이브러리 추가
- 프로젝트 패키지 구조 생성
- Git 브랜치 설정

---

### Step 1.1: Firebase 프로젝트 생성 (30분)

#### 왜 필요한가요?
Firebase는 우리 앱의 데이터베이스 역할을 합니다. 간병 신청 정보와 간병사 정보를 저장하고 불러올 수 있습니다.

#### 어떻게 하나요?

**1. Firebase Console 접속**
- https://console.firebase.google.com/ 접속
- Google 계정으로 로그인

**2. 새 프로젝트 생성**
```
1. "프로젝트 추가" 클릭
2. 프로젝트 이름: "Ganbyeong24" 입력
3. Google Analytics: 사용 안 함 (나중에 추가 가능)
4. "프로젝트 만들기" 클릭
```

**3. Android 앱 추가**
```
1. Android 아이콘 클릭
2. Android 패키지 이름: com.ganbyeong24.app
3. 앱 닉네임: 간병24
4. 디버그 서명 인증서 SHA-1: (지금은 건너뛰기)
5. "앱 등록" 클릭
```

**4. google-services.json 다운로드**
```
1. google-services.json 파일 다운로드
2. Android Studio에서 프로젝트 뷰를 "Project"로 변경
3. app/ 폴더에 google-services.json 파일 복사
```

> ⚠️ **주의**: google-services.json 파일은 절대 Git에 커밋하지 마세요!

**5. Firestore Database 생성**
```
1. Firebase Console에서 "Firestore Database" 메뉴 클릭
2. "데이터베이스 만들기" 클릭
3. 위치: asia-northeast3 (서울) 선택
4. 보안 규칙: "테스트 모드에서 시작" 선택 (나중에 변경)
5. "사용 설정" 클릭
```

✅ **완료 확인**: Firebase Console에서 Firestore Database가 생성되었는지 확인

---

### Step 1.2: 의존성 추가 (30분)

#### 왜 필요한가요?
앱 개발에 필요한 라이브러리들을 프로젝트에 추가합니다.

#### 어떻게 하나요?

**1. build.gradle.kts (Project 레벨) 수정**

파일 위치: `Ganbyeong24/build.gradle.kts`

```kotlin
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}
```

**2. build.gradle.kts (App 레벨) 수정**

파일 위치: `Ganbyeong24/app/build.gradle.kts`

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")  // 이 줄 추가
}

android {
    namespace = "com.ganbyeong24.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ganbyeong24.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Koin
    implementation("io.insert-koin:koin-android:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
}
```

**3. Sync Project**
```
1. Android Studio 상단의 "Sync Now" 클릭
2. 빌드가 성공하는지 확인
```

> 💡 **팁**: 에러가 발생하면 Android Studio를 재시작해보세요.

✅ **완료 확인**: Build 탭에서 "BUILD SUCCESSFUL" 메시지 확인

---

### Step 1.3: 패키지 구조 생성 (20분)

#### 왜 필요한가요?
코드를 체계적으로 관리하기 위해 폴더(패키지) 구조를 미리 만듭니다.

#### 어떻게 하나요?

**1. 기본 패키지 생성**

`app/src/main/java/com/ganbyeong24/app/` 아래에 다음 패키지들을 생성하세요:

```
com.ganbyeong24.app/
├── di/
├── data/
│   ├── model/
│   └── repository/
├── presentation/
│   ├── theme/
│   ├── components/
│   ├── navigation/
│   └── screens/
│       ├── splash/
│       ├── role/
│       ├── care_request/
│       ├── caregiver/
│       └── result/
└── util/
```

**패키지 생성 방법**:
```
1. app/src/main/java/com/ganbyeong24/app 우클릭
2. New → Package 선택
3. 패키지 이름 입력 (예: di)
4. Enter
```

✅ **완료 확인**: Project 뷰에서 모든 패키지가 생성되었는지 확인

---

### Step 1.4: Git 브랜치 설정 (15분)

#### 왜 필요한가요?
코드 버전 관리를 체계적으로 하기 위해 브랜치 전략을 적용합니다.

#### 어떻게 하나요?

**1. develop 브랜치 생성**
```bash
git checkout -b develop
git push origin develop
```

**2. .gitignore 확인**

`.gitignore` 파일에 다음 내용이 있는지 확인:
```
# Firebase
google-services.json

# Local configuration
local.properties
```

**3. 현재까지 작업 커밋**
```bash
git add .
git commit -m "chore: Setup project with Firebase and dependencies"
git push origin develop
```

✅ **완료 확인**: GitHub/GitLab에서 develop 브랜치 확인

---

## 📅 2단계: 기반 구축 (2-3일)

### 목표
- 테마 시스템 구축
- 공통 컴포넌트 개발
- Navigation 구조 설정
- Koin 의존성 주입 설정

---

### Step 2.1: 테마 시스템 구축 (1일)

#### 왜 필요한가요?
앱 전체에서 일관된 색상, 폰트, 스타일을 사용하기 위해 테마를 먼저 만듭니다.

#### 어떻게 하나요?

**1. Color.kt 생성**

파일 위치: `presentation/theme/Color.kt`

```kotlin
package com.ganbyeong24.app.presentation.theme

import androidx.compose.ui.graphics.Color

// Primary Colors (파란색 계열)
val Primary = Color(0xFF2196F3)
val PrimaryDark = Color(0xFF1976D2)
val PrimaryLight = Color(0xFFBBDEFB)

// Secondary Colors
val Secondary = Color(0xFF4CAF50)
val Error = Color(0xFFF44336)

// Neutral Colors
val Background = Color(0xFFFFFFFF)
val Surface = Color(0xFFF5F5F5)
val TextPrimary = Color(0xFF212121)
val TextSecondary = Color(0xFF757575)
```

**2. Type.kt 생성**

파일 위치: `presentation/theme/Type.kt`

```kotlin
package com.ganbyeong24.app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    )
)
```

**3. Theme.kt 생성**

파일 위치: `presentation/theme/Theme.kt`

```kotlin
package com.ganbyeong24.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    secondary = Secondary,
    error = Error,
    background = Background,
    surface = Surface,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun GanbyeongTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
```

**4. 테스트**

`MainActivity.kt`에서 테마 적용 확인:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GanbyeongTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("테마 테스트", style = MaterialTheme.typography.headlineLarge)
                }
            }
        }
    }
}
```

✅ **완료 확인**: 앱 실행 시 파란색 테마가 적용되는지 확인

---

### Step 2.2: 공통 컴포넌트 개발 (1일)

#### 왜 필요한가요?
여러 화면에서 반복적으로 사용되는 버튼, 입력 필드 등을 미리 만들어 재사용합니다.

#### 어떻게 하나요?

**1. GanbyeongButton.kt 생성**

파일 위치: `presentation/components/GanbyeongButton.kt`

```kotlin
package com.ganbyeong24.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganbyeong24.app.presentation.theme.GanbyeongTheme

@Composable
fun GanbyeongButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GanbyeongButtonPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            GanbyeongButton(
                text = "신청하기",
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            GanbyeongButton(
                text = "로딩 중",
                onClick = {},
                isLoading = true
            )
        }
    }
}
```

**2. GanbyeongTextField.kt 생성**

파일 위치: `presentation/components/GanbyeongTextField.kt`

```kotlin
package com.ganbyeong24.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganbyeong24.app.presentation.theme.GanbyeongTheme

@Composable
fun GanbyeongTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = RoundedCornerShape(4.dp)
        )
        
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GanbyeongTextFieldPreview() {
    GanbyeongTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            GanbyeongTextField(
                value = "",
                onValueChange = {},
                label = "환자명 *",
                placeholder = "예: 홍길동"
            )
            Spacer(modifier = Modifier.height(16.dp))
            GanbyeongTextField(
                value = "홍",
                onValueChange = {},
                label = "환자명 *",
                isError = true,
                errorMessage = "환자명은 2자 이상 입력해주세요"
            )
        }
    }
}
```

**3. Preview 확인**

Android Studio에서:
```
1. 파일 우측의 "Split" 버튼 클릭
2. Preview 패널에서 컴포넌트 확인
```

✅ **완료 확인**: Preview에서 버튼과 입력 필드가 제대로 보이는지 확인

---

### Step 2.3: Navigation 구조 설정 (반나절)

#### 왜 필요한가요?
화면 간 이동을 관리하기 위한 Navigation 구조를 만듭니다.

#### 어떻게 하나요?

**1. Screen.kt 생성**

파일 위치: `presentation/navigation/Screen.kt`

```kotlin
package com.ganbyeong24.app.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object RoleSelection : Screen("role_selection")
    object CareRequest : Screen("care_request")
    object CaregiverRegistration : Screen("caregiver_registration")
    object Result : Screen("result/{userRole}") {
        fun createRoute(userRole: String) = "result/$userRole"
    }
}
```

**2. NavGraph.kt 생성**

파일 위치: `presentation/navigation/NavGraph.kt`

```kotlin
package com.ganbyeong24.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun GanbyeongNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // 화면들은 나중에 추가
    }
}
```

✅ **완료 확인**: 빌드 에러 없이 컴파일되는지 확인

---

### Step 2.4: Koin 설정 (반나절)

#### 왜 필요한가요?
의존성 주입을 통해 코드를 깔끔하게 관리합니다.

#### 어떻게 하나요?

**1. AppModule.kt 생성**

파일 위치: `di/AppModule.kt`

```kotlin
package com.ganbyeong24.app.di

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val appModule = module {
    // Firebase
    single { FirebaseFirestore.getInstance() }
    
    // Repository와 ViewModel은 나중에 추가
}
```

**2. Application 클래스 생성**

파일 위치: `GanbyeongApplication.kt` (app 패키지 바로 아래)

```kotlin
package com.ganbyeong24.app

import android.app.Application
import com.ganbyeong24.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GanbyeongApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@GanbyeongApplication)
            modules(appModule)
        }
    }
}
```

**3. AndroidManifest.xml 수정**

파일 위치: `app/src/main/AndroidManifest.xml`

```xml
<application
    android:name=".GanbyeongApplication"
    ...>
```

✅ **완료 확인**: 앱 실행 시 Logcat에서 Koin 초기화 로그 확인

---

## 📅 3단계: 화면 개발 (1주일)

### 목표
- 5개 화면 UI 개발
- 화면 간 Navigation 연결

---

### Step 3.1: SplashScreen 개발 (반나절)

#### 왜 필요한가요?
앱 시작 시 보여줄 인트로 화면입니다.

#### 어떻게 하나요?

**1. SplashScreen.kt 생성**

파일 위치: `presentation/screens/splash/SplashScreen.kt`

```kotlin
package com.ganbyeong24.app.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganbyeong24.app.presentation.theme.GanbyeongTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToRoleSelection: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000) // 2초 대기
        onNavigateToRoleSelection()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 로고 (나중에 이미지로 교체)
            Text(
                text = "💙",
                fontSize = 80.sp
            )
            
            // 앱 이름
            Text(
                text = "간병24",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            
            // 로딩 인디케이터
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        
        // 버전 정보
        Text(
            text = "v1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    GanbyeongTheme {
        SplashScreen(onNavigateToRoleSelection = {})
    }
}
```

**2. NavGraph에 추가**

`NavGraph.kt` 수정:

```kotlin
composable(Screen.Splash.route) {
    SplashScreen(
        onNavigateToRoleSelection = {
            navController.navigate(Screen.RoleSelection.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    )
}
```

**3. MainActivity에서 NavGraph 사용**

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GanbyeongTheme {
                GanbyeongNavGraph()
            }
        }
    }
}
```

✅ **완료 확인**: 앱 실행 시 Splash 화면이 2초간 보이는지 확인

---

### Step 3.2: RoleSelectionScreen 개발 (반나절)

**파일 위치**: `presentation/screens/role/RoleSelectionScreen.kt`

> 💡 **팁**: 이 화면은 ScreenDesign.md 문서의 코드를 참고하세요!

✅ **완료 확인**: 두 개의 버튼이 잘 보이고 클릭 시 다음 화면으로 이동하는지 확인

---

### Step 3.3-3.5: 나머지 화면 개발

나머지 화면들은 다음 순서로 개발합니다:
1. CareRequestScreen (2일)
2. CaregiverRegistrationScreen (2일)
3. ResultScreen (반나절)

각 화면은 `ScreenDesign.md` 문서의 코드를 참고하여 개발하세요.

---

## 📅 4단계: 데이터 레이어 (3-4일)

### 목표
- 데이터 모델 생성
- Repository 구현
- ViewModel 구현
- Firebase 연동

---

### Step 4.1: 데이터 모델 생성 (반나절)

**1. CareRequest.kt 생성**

파일 위치: `data/model/CareRequest.kt`

```kotlin
package com.ganbyeong24.app.data.model

import com.google.firebase.Timestamp

data class CareRequest(
    val id: String = "",
    val patientName: String = "",
    val guardianName: String = "",
    val patientCondition: String = "",
    val careStartDate: Timestamp? = null,
    val careEndDate: Timestamp? = null,
    val location: String = "",
    val patientPhoneNumber: String? = null,
    val guardianPhoneNumber: String = "",
    val status: String = "pending",
    val createdAt: Timestamp = Timestamp.now(),
    val matchedCaregiverId: String? = null
)
```

**2. Caregiver.kt 생성**

파일 위치: `data/model/Caregiver.kt`

```kotlin
package com.ganbyeong24.app.data.model

import com.google.firebase.Timestamp

data class Caregiver(
    val id: String = "",
    val name: String = "",
    val experience: String = "",
    val certificates: List<String> = emptyList(),
    val availableRegions: List<String> = emptyList(),
    val phoneNumber: String = "",
    val status: String = "pending",
    val createdAt: Timestamp = Timestamp.now()
)
```

✅ **완료 확인**: 빌드 에러 없이 컴파일되는지 확인

---

### Step 4.2-4.4: Repository 및 ViewModel 구현

이 부분은 `TechnicalDesign.md` 문서를 참고하여 구현하세요.

---

## 📅 5단계: 테스트 및 배포 (3-4일)

### 목표
- 기능 테스트
- 버그 수정
- 앱 아이콘 추가
- APK 빌드

---

### Step 5.1: 기능 테스트 (2일)

**테스트 체크리스트**:

- [ ] Splash 화면이 2초 후 자동으로 넘어가는가?
- [ ] 역할 선택 화면에서 두 버튼이 작동하는가?
- [ ] 간병 신청 화면에서 모든 입력 필드가 작동하는가?
- [ ] 유효성 검사가 제대로 작동하는가?
- [ ] Firebase에 데이터가 저장되는가?
- [ ] 완료 화면이 제대로 표시되는가?

---

### Step 5.2: APK 빌드 (반나절)

**1. Release APK 빌드**

```
1. Android Studio 메뉴: Build → Generate Signed Bundle / APK
2. APK 선택
3. Create new... 클릭하여 키스토어 생성
4. 정보 입력 후 Next
5. release 선택
6. Finish
```

**2. APK 위치**

```
app/release/app-release.apk
```

✅ **완료 확인**: 실제 디바이스에 APK 설치 후 테스트

---

## 🎯 다음 단계

개발이 완료되면:

1. **Google Play Console 등록**
2. **앱 스토어 등록 준비**
3. **사용자 피드백 수집**
4. **v2.0 기능 개발** (자동 매칭, 채팅 등)

---

## 📚 참고 문서

- [PRD.md](./PRD.md) - 프로젝트 기획서
- [TechnicalDesign.md](./TechnicalDesign.md) - 기술 설계서
- [ScreenDesign.md](./ScreenDesign.md) - 화면 설계서
- [DevelopmentGuide.md](./DevelopmentGuide.md) - 개발 가이드

---

## ❓ 자주 묻는 질문 (FAQ)

### Q1: 빌드 에러가 발생하면 어떻게 하나요?
**A**: 다음 순서로 시도해보세요:
1. Android Studio 재시작
2. File → Invalidate Caches / Restart
3. Build → Clean Project → Rebuild Project

### Q2: Firebase 연결이 안 되면?
**A**: 다음을 확인하세요:
1. google-services.json 파일이 app/ 폴더에 있는지
2. 패키지 이름이 일치하는지 (com.ganbyeong24.app)
3. 인터넷 연결 확인

### Q3: Preview가 안 보이면?
**A**: 
1. Android Studio를 최신 버전으로 업데이트
2. Split 모드로 변경
3. Build → Refresh

---

## 🔄 버전 관리

| 버전 | 날짜 | 작성자 | 변경 사항 |
|------|------|--------|-----------|
| 1.0 | 2026-01-14 | Development Team | 초기 로드맵 작성 |

---

**문서 작성일**: 2026년 1월 14일  
**최종 수정일**: 2026년 1월 14일  
**문서 상태**: 초안 (Draft)
