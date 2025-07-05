plugins {
    kotlin("jvm")
}
// Top-level build.gradle.kts

buildscript {
    val kotlin_version = "1.9.22" // ✅ Update Kotlin version

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.9.1") // ✅ Use official stable AGP version
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}