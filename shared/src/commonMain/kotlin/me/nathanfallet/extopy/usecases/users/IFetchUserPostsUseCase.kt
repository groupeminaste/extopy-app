package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.IPairSuspendUseCase
import me.nathanfallet.usecases.pagination.Pagination

interface IFetchUserPostsUseCase : IPairSuspendUseCase<String, Pagination, List<Post>>
