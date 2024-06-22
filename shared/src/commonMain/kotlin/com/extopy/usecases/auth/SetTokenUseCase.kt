package com.extopy.usecases.auth

import com.extopy.models.auth.AuthToken
import com.extopy.repositories.application.ITokenRepository

class SetTokenUseCase(
    private val tokenRepository: ITokenRepository,
) : ISetTokenUseCase {

    override fun invoke(input: AuthToken?) {
        tokenRepository.setToken(input?.accessToken)
        tokenRepository.setRefreshToken(input?.refreshToken)
    }

}
