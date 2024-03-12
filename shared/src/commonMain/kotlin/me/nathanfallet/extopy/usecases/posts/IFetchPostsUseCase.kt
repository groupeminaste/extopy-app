package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.ISuspendUseCase
import me.nathanfallet.usecases.pagination.Pagination

interface IFetchPostsUseCase : ISuspendUseCase<Pagination, List<Post>>
