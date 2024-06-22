package com.extopy.usecases.auth

import com.extopy.repositories.application.ITokenRepository
import dev.kaccelero.models.UUID

class GetUserIdUseCase(
    private val tokenRepository: ITokenRepository,
) : IGetUserIdUseCase {

    override fun invoke(): UUID? = tokenRepository.getUserId()

}
