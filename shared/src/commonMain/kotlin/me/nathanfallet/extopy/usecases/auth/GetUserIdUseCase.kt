package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.extopy.repositories.application.ITokenRepository

class GetUserIdUseCase(
    private val tokenRepository: ITokenRepository,
) : IGetUserIdUseCase {

    override fun invoke(): String? {
        return tokenRepository.getUserId()
    }

}
