package com.extopy.data.api

import com.extopy.domain.models.application.ApiException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import kotlin.coroutines.cancellation.CancellationException

/**
 * Runs an API call and rewrites whatever it threw as an [ApiException].
 *
 * Cancellation is re-thrown untouched — it is how a coroutine is torn down, not a failure, and
 * turning it into an error would have a screen the user just left report a problem.
 */
internal suspend fun <T> api(block: suspend () -> T): T = try {
    block()
} catch (e: CancellationException) {
    throw e
} catch (e: ResponseException) {
    throw ApiException(e.response.status.value, e)
} catch (e: Throwable) {
    // No answer at all: no network, DNS, timeout, a body that would not parse.
    throw ApiException(cause = e)
}

/**
 * Same as [api], but "there is no such thing" comes back as null rather than as a failure — which
 * is what a caller asking for one post by id wants.
 */
internal suspend fun <T : Any> apiOrNull(block: suspend () -> T): T? = try {
    api(block)
} catch (e: ApiException) {
    if (e.status == HttpStatusCode.NotFound.value) null else throw e
}
