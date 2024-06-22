package com.extopy.usecases.auth

import dev.kaccelero.commons.auth.ILogoutUseCase

class LogoutUseCase(
    private val setTokenUseCase: ISetTokenUseCase,
    private val setUserIdUseCase: ISetUserIdUseCase,
    //private val clearFcmTokenUseCase: IClearFcmTokenUseCase,
) : ILogoutUseCase {

    override fun invoke() {
        setTokenUseCase(null)
        setUserIdUseCase(null)
        //clearFcmTokenUseCase()
        // TODO: Clear cache
    }

}
