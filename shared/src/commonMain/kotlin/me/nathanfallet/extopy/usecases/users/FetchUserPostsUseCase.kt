package me.nathanfallet.extopy.usecases.users

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.repositories.posts.IPostsRepository
import me.nathanfallet.usecases.pagination.Pagination

class FetchUserPostsUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : IFetchUserPostsUseCase {

    override suspend fun invoke(input1: String, input2: Pagination): List<Post> =
        client.users.getPosts(input1, input2).onEach {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
