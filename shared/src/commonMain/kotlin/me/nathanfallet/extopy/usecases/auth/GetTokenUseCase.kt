package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.extopy.repositories.application.ITokenRepository
import me.nathanfallet.ktorx.usecases.api.IGetTokenUseCase

class GetTokenUseCase(
    private val tokenRepository: ITokenRepository,
) : IGetTokenUseCase {

    override fun invoke(): String? {
        return tokenRepository.getToken()
    }

}
