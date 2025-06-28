package com.extopy.usecases.auth

import com.extopy.repositories.application.IApplicationRepository
import dev.kaccelero.models.UUID

class GetUserIdUseCase(
    private val tokenRepository: IApplicationRepository,
) : IGetUserIdUseCase {

    override fun invoke(): UUID? = tokenRepository.getUserId()

}
