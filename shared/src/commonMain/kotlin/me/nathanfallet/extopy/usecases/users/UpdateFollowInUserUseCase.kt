package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.auth.IGetUserIdUseCase

class UpdateFollowInUserUseCase(
    private val client: IExtopyClient,
    private val getUserIdUseCase: IGetUserIdUseCase,
) : IUpdateFollowInUserUseCase {

    override suspend fun invoke(input: User): User? {
        val userId = getUserIdUseCase() ?: return null
        if (input.followersIn == true) client.followersInUsers.delete(input.id, userId)
        else client.followersInUsers.create(input.id)
        return input.copy(
            followersIn = input.followersIn != true,
            followersCount = (input.followersCount ?: 0) + if (input.followersIn == true) -1 else 1
        )
    }

}
