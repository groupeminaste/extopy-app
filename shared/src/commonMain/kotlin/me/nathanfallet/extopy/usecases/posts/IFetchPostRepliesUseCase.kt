package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.IPairSuspendUseCase
import me.nathanfallet.usecases.pagination.Pagination

interface IFetchPostRepliesUseCase : IPairSuspendUseCase<String, Pagination, List<Post>>
