package com.extopy.usecases.auth

import com.extopy.repositories.application.ITokenRepository
import dev.kaccelero.commons.auth.IGetTokenUseCase

class GetTokenUseCase(
    private val tokenRepository: ITokenRepository,
) : IGetTokenUseCase {

    override fun invoke(): String? = tokenRepository.getToken()

}
