plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // ✅ Room, Dagger 등에 필요한 Kotlin Annotation Processor
    id("kotlin-kapt")
}

android {
    namespace = "com.glowstudio.android.blindsjn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.glowstudio.android.blindsjn"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true // ✅ Java 8+ API를 낮은 SDK에서도 사용
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // ✅ AndroidX, Compose, Material3, Navigation, Test 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ✅ Compose Core 및 Material3
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.activity:activity-compose:1.9.3")

    // ✅ Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.8.4")

    // ✅ Icons
    implementation("androidx.compose.material:material-icons-core:1.7.5")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // ✅ JSON serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // ✅ Retrofit, OkHttp, Gson (네트워크 통신)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // ✅ Coroutines (비동기 처리)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ✅ ViewModel, LiveData (MVVM 구조)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // ✅ Room (로컬 데이터베이스)
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1") // ✅ kapt 오류 발생 방지
    implementation("androidx.room:room-ktx:2.6.1")

    // ✅ DataStore (자동 로그인용 저장소)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ✅ Coil (이미지 로딩)
    implementation("io.coil-kt:coil-compose:2.1.0")

    // ✅ Tess-Two (OCR 기능)
    implementation("com.rmtheis:tess-two:9.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ✅ Java 8+ 기능을 하위 SDK에서 사용
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
}
