package com.extopy.data.api

import com.extopy.domain.models.application.ExtopyEnvironment
import com.extopy.api.models.application.ExtopyEnvironment as ApiEnvironment

/**
 * Where each environment actually lives.
 *
 * The two deployed ones come from the API's own enum, so a deployment that moves is picked up by
 * bumping the dependency rather than by editing the app.
 */
val ExtopyEnvironment.baseUrl: String
    get() = when (this) {
        ExtopyEnvironment.PRODUCTION -> ApiEnvironment.PRODUCTION.baseUrl
        ExtopyEnvironment.DEVELOPMENT -> ApiEnvironment.DEVELOPMENT.baseUrl
        ExtopyEnvironment.LOCAL -> localBaseUrl
    }

/**
 * How a device reaches a backend running on the developer's machine.
 *
 * Not the same address on both platforms: the iOS simulator shares the host's network stack, the
 * Android emulator does not and maps the host to a fixed address of its own.
 */
expect val localBaseUrl: String

/** The port `extopy-backend` listens on by default. */
internal const val LOCAL_PORT = 8080
