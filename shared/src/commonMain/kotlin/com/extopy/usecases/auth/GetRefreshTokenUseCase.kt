package com.extopy.usecases.auth

import com.extopy.repositories.application.ITokenRepository

class GetRefreshTokenUseCase(
    private val tokenRepository: ITokenRepository,
) : IGetRefreshTokenUseCase {

    override fun invoke() = tokenRepository.getRefreshToken()

}
