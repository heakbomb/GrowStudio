plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kotlin serialization plugin for type safe routes and navigation arguments
    // 객체 직렬화를 위해 사용. Json 라이브러리
    kotlin("plugin.serialization") version "2.0.21"
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

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

    // Navigation
    implementation ("androidx.navigation:navigation-compose:2.8.4")
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.4")

    // Json serialization (DB 전송하려면 필요함)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Retrofit library (서버 통신에 필요)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // 자동로그인을 위한 DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    // Kotlin Coroutines (DataStore는 비동기 작업을 위해 Coroutines 사용)
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // text 추출을 위한 tess-two
    implementation ("com.rmtheis:tess-two:9.1.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
}