plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlinx.kover")
    id("com.google.devtools.ksp")
    id("app.cash.sqldelight")
    id("com.rickclephas.kmp.nativecoroutines")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    val ktorVersion = "3.1.3"
    val koinVersion = "4.1.0"
    val sqlDelightVersion = "2.0.0"
    val compottieVersion = "2.0.0-rc04"
    val kacceleroVersion = "0.4.4"

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.experimental.ExperimentalObjCName")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-websockets:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.insert-koin:koin-compose:$koinVersion")
                implementation("io.insert-koin:koin-compose-viewmodel:$koinVersion")
                implementation("io.insert-koin:koin-compose-viewmodel-navigation:$koinVersion")
                implementation("io.sentry:sentry-kotlin-multiplatform:0.7.1")

                implementation("app.cash.sqldelight:runtime:$sqlDelightVersion")
                implementation("co.touchlab:stately-common:2.0.5")

                implementation("io.github.alexzhirkevich:compottie:$compottieVersion")
                implementation("io.github.alexzhirkevich:compottie-resources:$compottieVersion")
                implementation("io.coil-kt.coil3:coil-compose:3.2.0")

                api(compose.runtime)
                api(compose.foundation)
                api(compose.animation)
                api(compose.material3)
                api(compose.components.resources)
                api(compose.components.uiToolingPreview)

                api("dev.kaccelero:settings:${kacceleroVersion}")
                api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-7")
                api("com.extopy:commons:0.1.4")
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.mockk:mockk:1.13.11")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.extopy.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kover {
    currentProject {
        createVariant("custom") {
            addWithDependencies("debug")
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.extopy.database")
        }
    }
}
