package com.extopy.data.repositories

import com.extopy.api.requests.auth.AuthRequest
import com.extopy.client.ExtopyClient
import com.extopy.data.api.api
import com.extopy.data.api.baseUrl
import com.extopy.domain.models.application.ExtopyEnvironment
import com.extopy.domain.repositories.AuthRepository
import kotlin.uuid.Uuid

/**
 * Exchanges OAuth authorization codes for a session.
 *
 * Storing the tokens is not this class's job: the client writes them to its own [com.extopy.client.auth.TokenStore]
 * as part of the exchange, and refreshes them on its own from then on. All that is left to hand
 * back is who the session belongs to.
 */
class ApiAuthRepository(
    private val client: ExtopyClient,
    private val environment: ExtopyEnvironment,
) : AuthRepository {

    override val authorizationUrl: String
        get() = "${environment.baseUrl}/auth/authorize?clientId=$CLIENT_ID"

    override suspend fun authenticate(code: String): Uuid =
        api {
            client.auth.token(
                AuthRequest(
                    clientId = Uuid.parse(CLIENT_ID),
                    clientSecret = CLIENT_SECRET,
                    code = code,
                )
            )
        }.idToken

    override suspend fun logout() {
        client.auth.logout()
    }

    companion object {
        /**
         * The credentials of the first-party client. Not a secret in any meaningful sense — it ships
         * inside the binary — which is why the authorization code flow is what actually authenticates
         * the user.
         */
        private const val CLIENT_ID = "00000000-0000-0000-0000-000000000000"
        private const val CLIENT_SECRET = "GAZgLpJ4zsXKC2Br6vtTEMnPYwR587dW"
    }

}
