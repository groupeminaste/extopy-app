package com.extopy.domain.models.application

/**
 * The API turned a call down.
 *
 * The data layer translates whatever the HTTP client threw into this, so that nothing above it has
 * to know Ktor exists. [status] is the HTTP status code, or `null` when the call never got an
 * answer at all (no network, timeout).
 */
class ApiException(
    val status: Int? = null,
    cause: Throwable? = null,
) : Exception("The Extopy API answered ${status ?: "nothing"}", cause) {

    /** The session is gone or was never valid — the caller should sign the user out. */
    val unauthorized: Boolean
        get() = status == 401

}
