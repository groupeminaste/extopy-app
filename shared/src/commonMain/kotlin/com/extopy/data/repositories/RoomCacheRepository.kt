package com.extopy.data.repositories

import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.domain.repositories.CacheRepository
import kotlin.time.Clock

class RoomCacheRepository(
    private val users: UserDao,
    private val posts: PostDao,
) : CacheRepository {

    override suspend fun clear() {
        posts.deleteAll()
        users.deleteAll()
    }

    override suspend fun purgeExpired() {
        val now = Clock.System.now().toEpochMilliseconds()
        posts.deleteExpired(now)
        users.deleteExpired(now)
    }

}
