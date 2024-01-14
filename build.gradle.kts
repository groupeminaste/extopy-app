plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.0").apply(false)
    id("com.android.library").version("8.1.0").apply(false)
    kotlin("android").version("1.9.20").apply(false)
    kotlin("multiplatform").version("1.9.20").apply(false)
    kotlin("plugin.serialization").version("1.9.20").apply(false)
    id("org.jetbrains.kotlinx.kover").version("0.7.4")
    id("com.google.devtools.ksp").version("1.9.20-1.0.13").apply(false)
    id("org.jetbrains.compose").version("1.5.10").apply(false)
    id("app.cash.sqldelight").version("2.0.0").apply(false)
    id("com.rickclephas.kmp.nativecoroutines").version("1.0.0-ALPHA-19").apply(false)
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
