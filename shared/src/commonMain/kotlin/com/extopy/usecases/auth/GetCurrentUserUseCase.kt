package com.extopy.usecases.auth

import com.extopy.usecases.users.IFetchUserUseCase

class GetCurrentUserUseCase(
    private val getUserIdUseCase: IGetUserIdUseCase,
    private val fetchUserUseCase: IFetchUserUseCase,
) : IGetCurrentUserUseCase {

    override suspend fun invoke() = getUserIdUseCase()?.let { fetchUserUseCase(it) }

}
