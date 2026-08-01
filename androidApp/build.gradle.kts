import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(projects.shared)

    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "com.extopy"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "me.nathanfallet.extopy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 6
        versionName = "0.1.1"
    }
    buildFeatures {
        buildConfig = true
    }
    // The keystore and its passwords only exist on CI, which writes the file from a secret before
    // building. A release built anywhere else comes out unsigned rather than failing, so that
    // `assembleProductionRelease` still works locally for checking what ships.
    val keystore = file("fastlane/keystore.jks")
    signingConfigs {
        create("release") {
            if (keystore.exists()) {
                storeFile = keystore
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            }
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,INDEX.LIST}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs["release"].takeIf { keystore.exists() }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    flavorDimensions += listOf("env")
    productFlavors {
        create("production") {
            dimension = "env"
        }
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
        }
        // Never released: talks to a backend on the developer's machine over cleartext HTTP, which
        // is why it carries its own manifest allowing that. Run it with
        // `./gradlew :androidApp:installLocalDebug`.
        create("local") {
            dimension = "env"
            applicationIdSuffix = ".local"
        }
    }
}
