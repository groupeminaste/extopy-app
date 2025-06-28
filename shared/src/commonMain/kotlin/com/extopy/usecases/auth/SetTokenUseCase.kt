package com.extopy.usecases.auth

import com.extopy.models.auth.AuthToken
import com.extopy.repositories.application.IApplicationRepository

class SetTokenUseCase(
    private val tokenRepository: IApplicationRepository,
) : ISetTokenUseCase {

    override fun invoke(input: AuthToken?) {
        tokenRepository.setToken(input?.accessToken)
        tokenRepository.setRefreshToken(input?.refreshToken)
    }

}
