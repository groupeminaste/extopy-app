package com.extopy.usecases.auth

import com.extopy.repositories.application.IApplicationRepository
import dev.kaccelero.commons.auth.IGetTokenUseCase

class GetTokenUseCase(
    private val tokenRepository: IApplicationRepository,
) : IGetTokenUseCase {

    override fun invoke(): String? = tokenRepository.getToken()

}
