package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.usecases.auth.IGetUserIdUseCase

class UpdateLikeInPostUseCase(
    private val client: IExtopyClient,
    private val getUserIdUseCase: IGetUserIdUseCase,
) : IUpdateLikeInPostUseCase {

    override suspend fun invoke(input: Post): Post? {
        val userId = getUserIdUseCase() ?: return null
        if (input.likesIn == true) client.likesInPosts.delete(input.id, userId)
        else client.likesInPosts.create(input.id)
        return input.copy(
            likesIn = input.likesIn != true,
            likesCount = (input.likesCount ?: 0) + if (input.likesIn == true) -1 else 1
        )
    }

}
