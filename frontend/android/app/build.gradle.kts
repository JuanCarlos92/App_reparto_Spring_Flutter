// android/app/build.gradle.kts

import java.util.Properties

// Cargar variables desde env.gradle (o directamente desde .env)
val props = Properties()
file("../../assets/.env").inputStream().use { props.load(it) }
val googleMapsApiKey: String = props.getProperty("GOOGLE_MAPS_API_KEY") ?: ""

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.app_reparto"
    compileSdk = 35
    ndkVersion = "27.0.12077973"

    defaultConfig {
        applicationId = "com.example.app_reparto"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Kotlin DSL usa mapOf en vez de []
        manifestPlaceholders += mapOf(
            "GOOGLE_MAPS_API_KEY" to googleMapsApiKey
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}

dependencies {
    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")
}