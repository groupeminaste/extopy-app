package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.extopy.repositories.application.ITokenRepository

class SetUserIdUseCase(
    private val tokenRepository: ITokenRepository,
) : ISetUserIdUseCase {

    override fun invoke(input: String?) {
        tokenRepository.setUserId(input)
    }

}
