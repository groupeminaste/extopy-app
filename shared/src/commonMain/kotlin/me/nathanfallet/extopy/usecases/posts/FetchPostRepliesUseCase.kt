package me.nathanfallet.extopy.usecases.posts

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.repositories.posts.IPostsRepository
import me.nathanfallet.usecases.pagination.Pagination

class FetchPostRepliesUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : IFetchPostRepliesUseCase {

    override suspend fun invoke(input1: String, input2: Pagination): List<Post> =
        client.posts.getReplies(input1, input2).onEach {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
