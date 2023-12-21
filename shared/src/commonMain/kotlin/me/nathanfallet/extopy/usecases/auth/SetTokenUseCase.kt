package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.extopy.repositories.application.ITokenRepository

class SetTokenUseCase(
    private val tokenRepository: ITokenRepository,
) : ISetTokenUseCase {

    override fun invoke(input: String?) {
        tokenRepository.setToken(input)
    }

}
