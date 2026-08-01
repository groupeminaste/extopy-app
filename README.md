# extopy-app

The Extopy mobile app, built with Kotlin Multiplatform and Compose Multiplatform. One shared
codebase, two thin platform shells.

## Modules

| Module | What it is |
|---|---|
| `shared` | Everything: data, domain, presentation, DI. Compiles to an Android library and an iOS framework. |
| `androidApp` | The Android application — an `Application` that starts Koin and an `Activity` that hosts `App()`. |
| `iosApp` | The Xcode project — a SwiftUI shell around `MainViewController()`. |

## Layers

Inside `shared`, under `com.extopy`:

```
data/          how things are actually fetched and stored
  api/         the HTTP client (com.extopy:client) and where the session tokens live
  database/    Room: the local cache, its entities and DAOs
  mappers/     API responses <-> domain models, domain models <-> cached rows
  repositories/  the implementations
domain/        what the app is about, and nothing else
  models/      Post, User, Timeline… — no serialization, no HTTP, no SQL
  repositories/  the interfaces the rest of the app talks to
  usecases/    only the operations that decide something
presentation/  Compose: components, navigation, screens, theme, view models
di/            the Koin modules
```

The rule that matters: `domain` and `presentation` never import `com.extopy.api.*`,
`com.extopy.client.*`, `io.ktor.*`, `androidx.room.*` or `com.russhwolf.*`. Everything the API
returns is translated in `data/mappers` before it goes any further, so a change to the API contract
stops at that boundary.

View models take repositories directly for plain reads; a use case exists only where there is real
logic — signing in, signing out, flipping a like or a follow.

## API

The models and the HTTP client come from [extopy-backend](https://github.com/groupeminaste/extopy-backend),
published to Maven Central as `com.extopy:api` and `com.extopy:client`. The client attaches the
access token to every call and refreshes it on its own; the app only supplies a `TokenStore` saying
where the tokens are kept — the keychain on iOS, a preferences file on Android.

## Running against a local backend

Start `extopy-backend` on port 8080, then:

**Android** — build the `local` flavor, which points at `10.0.2.2:8080` (how the emulator reaches
your machine) and is the only flavor allowed to talk over cleartext HTTP:

```sh
./gradlew :androidApp:installLocalDebug
```

**iOS** — in Xcode, edit the scheme and add the environment variable `EXTOPY_ENV=local`, then run a
debug build. It points at `localhost:8080`. The variable is ignored in release builds, so it cannot
ship by accident.

## Tests

```sh
./gradlew :shared:testAndroidHostTest
```

Everything under `commonTest` runs there. The iOS test binary cannot be linked outside Xcode — the
Sentry SDK it depends on is supplied by the Xcode project through SPM — so the shared tests run on
the JVM, which exercises the same code.

## Building

```sh
./gradlew :androidApp:assembleProductionDebug            # Android
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64    # iOS framework, then build in Xcode
```
