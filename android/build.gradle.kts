plugins {
    kotlin("android")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("com.android.application")
}

android {
    namespace = "com.extopy"
    compileSdk = 35
    defaultConfig {
        applicationId = "me.nathanfallet.extopy"
        minSdk = 21
        targetSdk = 35
        versionCode = 6
        versionName = "0.1.1"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,INDEX.LIST}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
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
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("io.insert-koin:koin-android:4.1.0")
    implementation("io.insert-koin:koin-androidx-compose:4.1.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}
