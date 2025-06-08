plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("android").version("2.1.21").apply(false)
    kotlin("multiplatform").version("2.1.21").apply(false)
    kotlin("plugin.serialization").version("2.1.21").apply(false)
    kotlin("plugin.compose").version("2.1.21").apply(false)
    id("com.android.application").version("8.2.1").apply(false)
    id("com.android.library").version("8.2.1").apply(false)
    id("org.jetbrains.kotlinx.kover").version("0.8.0")
    id("com.google.devtools.ksp").version("2.1.21-2.0.1").apply(false)
    id("app.cash.sqldelight").version("2.0.0").apply(false)
    id("com.rickclephas.kmp.nativecoroutines").version("1.0.0-ALPHA-37").apply(false)
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
