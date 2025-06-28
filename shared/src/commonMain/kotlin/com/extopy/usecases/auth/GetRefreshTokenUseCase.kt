package com.extopy.usecases.auth

import com.extopy.repositories.application.IApplicationRepository

class GetRefreshTokenUseCase(
    private val tokenRepository: IApplicationRepository,
) : IGetRefreshTokenUseCase {

    override fun invoke() = tokenRepository.getRefreshToken()

}
