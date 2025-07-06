pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.0.21"
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" // ✅ Required for Compose with Kotlin 2.0+
        id("com.android.application") version "8.4.2"               // ✅ Adjust if you're using a newer AGP
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    // Uncomment this if you plan to use version catalogs (libs.versions.toml)
    // versionCatalogs {
    //     create("libs") {
    //         from(files("gradle/libs.versions.toml"))
    //     }
    // }
}

rootProject.name = "StratizenCore"
include(":app") // ✅ Correct module name
