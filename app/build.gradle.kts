plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" // Kotlin 2.0+ Compose plugin
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // ✅ Core Versions
    val lifecycle_version = "2.7.0"
    val room_version = "2.6.1"
    val nav_version = "2.7.7"
    val work_version = "2.9.0"
    val coroutines_version = "1.7.3"

    // ✅ Compose BOM (recommended for version alignment)
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))

    // ✅ Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material") // legacy M2 support if needed
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3") // latest version from BOM
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // ✅ Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // ✅ Lifecycle + ViewModel + Compose integrations
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version") // 👈 Required for collectAsStateWithLifecycle

    // ✅ Room Database
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // ✅ Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // ✅ Android Core & Activity Compose
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.0")

    // ✅ WorkManager
    implementation("androidx.work:work-runtime-ktx:$work_version")

    // ✅ DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ✅ LiveData to Compose support
    implementation("androidx.compose.runtime:runtime-livedata")
}
