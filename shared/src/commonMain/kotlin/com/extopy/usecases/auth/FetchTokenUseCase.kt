package com.extopy.usecases.auth

import com.extopy.client.IExtopyClient
import com.extopy.models.application.ExtopyEnvironment
import com.extopy.models.auth.AuthRequest
import com.extopy.models.auth.AuthToken
import dev.kaccelero.models.UUID

class FetchTokenUseCase(
    private val environment: ExtopyEnvironment,
    private val client: IExtopyClient,
    private val setTokenUseCase: ISetTokenUseCase,
    private val setUserIdUseCase: ISetUserIdUseCase,
) : IFetchTokenUseCase {

    override suspend fun invoke(input: String): AuthToken? {
        return client.auth.token(
            AuthRequest(
                UUID("00000000-0000-0000-0000-000000000000"),
                "GAZgLpJ4zsXKC2Br6vtTEMnPYwR587dW",
                input
            )
        )?.also {
            setTokenUseCase(it)
            setUserIdUseCase(it.idToken)
        }
    }

}
