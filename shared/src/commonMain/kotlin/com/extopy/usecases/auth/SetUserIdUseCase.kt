package com.extopy.usecases.auth

import com.extopy.repositories.application.ITokenRepository
import dev.kaccelero.models.UUID

class SetUserIdUseCase(
    private val tokenRepository: ITokenRepository,
) : ISetUserIdUseCase {

    override fun invoke(input: UUID?) = tokenRepository.setUserId(input)

}
