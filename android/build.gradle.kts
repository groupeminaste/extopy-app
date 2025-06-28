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
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-tooling:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.compose.foundation:foundation:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")

    implementation("io.insert-koin:koin-android:4.1.0")
    implementation("io.insert-koin:koin-androidx-compose:4.1.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.JamalMulla:ComposePrefs3:1.0.4")
    implementation("me.nathanfallet.myapps:myapps-android-compose:1.3.3")
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
