rootProject.name = "extopy-app"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        // Snapshots of com.extopy:api and com.extopy:client, published by extopy-backend with
        // `./gradlew :api:publishToMavenLocal :client:publishToMavenLocal`. Nothing else is read there.
        mavenLocal {
            mavenContent {
                includeGroup("com.extopy")
                snapshotsOnly()
            }
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")
include(":shared")
