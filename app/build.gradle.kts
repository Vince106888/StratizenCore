// Module-level build.gradle.kts (app/build.gradle.kts)

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.stratizen.core"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stratizen.core"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    val lifecycle_version = "2.7.0"
    val room_version = "2.6.1"
    val nav_version = "2.7.7"

    // Compose
    implementation("androidx.compose.ui:ui:1.6.1")
    implementation("androidx.compose.material:material:1.6.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.1")

    implementation(platform("androidx.compose:compose-bom:2024.01.00")) // ✅ BOM recommended
    implementation("androidx.compose.foundation:foundation") // Required for LazyColumn + stickyHeader

    // ✅ LiveData support for Compose (fixes observeAsState)
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Android Core
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Optional Material Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.1")

    // ✅ Material 3 (fixes your Theme.kt error)
    implementation("androidx.compose.material3:material3:1.2.1")


    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

}
