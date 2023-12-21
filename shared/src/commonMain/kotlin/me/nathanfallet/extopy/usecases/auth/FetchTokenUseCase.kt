package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.application.ExtopyEnvironment
import me.nathanfallet.usecases.auth.AuthRequest
import me.nathanfallet.usecases.auth.AuthToken

class FetchTokenUseCase(
    private val environment: ExtopyEnvironment,
    private val client: IExtopyClient,
) : IFetchTokenUseCase {

    override suspend fun invoke(input: String): AuthToken? {
        return client.auth.token(
            AuthRequest(
                "extopy",
                "secret", // TODO: Use a real secret from env
                input
            )
        ).also {
            println(it)
        }
    }

}
