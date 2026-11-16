# Extopy app: contributor and agent instructions

These rules hold for every change, by a person or by an agent. When a rule and the code disagree,
the rule wins and the code is fixed in the same PR, or the rule is changed on purpose in that PR.

## Working

- All repository content is in English, except the translated strings. Root package: `com.extopy`.
- Work on a branch, never on `main`; one subject per PR. PRs are squash-merged.
- Put the evidence in the PR description: tests run, what was checked by hand on Android and iOS,
  and screenshots for any visible change.
- Never stop processes or Gradle daemons you did not start (no `./gradlew --stop`, no `pkill java`).
- Build with JDK 21 and the checked-in Gradle wrapper. CI (`.github/workflows/build.yml`) is the
  authoritative list of commands.

## Choosing a library or a technology

- **Do not add a dependency, a Gradle plugin, an SDK, a service or a new technology on your own.**
  Ask Nathan first, with the options and their trade-offs; he decides. This matters most for the
  big pieces (camera, images and media, push notifications, storage): some already have an answer
  (the camera will be Nathan's own Kotlin Multiplatform camera library). Upgrading a version that
  is already in the catalog is fine.
- Every version lives in `gradle/libs.versions.toml`.

## The API

- The models and the HTTP client are `com.extopy:api` and `com.extopy:client`, published from
  `extopy-backend`. The app never declares its own copy of an endpoint or a response.
- `main` always builds against a **release from Maven Central**. To work against a backend change
  that is not released yet, publish a snapshot from the backend to Maven Local and build with
  `-PextopyVersion=0.2.0-SNAPSHOT` (README, "Using a backend snapshot"). Never commit a snapshot
  version: CI cannot see Maven Local. Releases on Central are occasional (monthly limits).

## Architecture

All the code is in `shared`; `androidApp` and `iosApp` are thin shells.

- `data` — how things are fetched and stored: the API client, Room (the local cache), settings,
  the mappers, the repository implementations.
- `domain` — what the app is about: models, repository interfaces, use cases.
- `presentation` — Compose: screens, components, navigation, theme, view models.
- `di` — the Koin modules, the only place where implementations are chosen.

Rules:

- `domain` and `presentation` never import `com.extopy.api.*`, `com.extopy.client.*`, `io.ktor.*`,
  `androidx.room.*` or `com.russhwolf.*`. Everything from the API is translated in `data/mappers`
  before it goes further, so a change of the API contract stops there.
- View models read repositories directly for plain reads. A use case exists only where there is a
  decision (signing in and out, toggling a like or a follow); it is tested.
- Screens are stateless where possible: state and events come from the view model, so they can be
  previewed and tested.
- Platform code (`androidMain`, `iosMain`) only for what cannot be common; prefer `expect`/`actual`
  over duplicating logic.
- Every user-visible string is a Compose resource in `values/strings.xml` (English); translations
  come from Weblate, never edited by hand in `values-*`.

## Code

- Kotlin official style. No `!!` in production code.
- Coroutines: never swallow `CancellationException` (rethrow it first in any broad `catch`, no
  `runCatching` around suspend calls). View models launch in `viewModelScope`.
- Errors from the API reach the user as a message, never as a crash or a silent empty screen.
- Lists are paged; never load an unbounded list.
- Never log tokens, passwords or personal data. Tokens live in the `TokenStore` (keychain on iOS).
- Common code compiles for iOS too: only `kotlin.*` and multiplatform libraries in `commonMain`
  (`kotlin.time.Instant`, `encodeToByteArray()`, no `java.*`, no `String.format`).

## Tests

- A bug fix starts with a test that fails without the fix.
- Mappers, repositories and use cases are tested in `shared/src/commonTest`
  (`./gradlew :shared:testAndroidHostTest`).
- Before a PR: `./gradlew :shared:testAndroidHostTest :androidApp:assembleProductionDebug`, and the
  iOS framework (`./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`) when common or iOS code
  changed.
