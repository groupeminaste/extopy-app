package com.extopy.usecases.auth

import com.extopy.repositories.application.IApplicationRepository
import dev.kaccelero.models.UUID

class SetUserIdUseCase(
    private val tokenRepository: IApplicationRepository,
) : ISetUserIdUseCase {

    override fun invoke(input: UUID?) = tokenRepository.setUserId(input)

}
