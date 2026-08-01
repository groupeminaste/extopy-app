package com.extopy.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.extopy.data.database.entities.PostEntity
import com.extopy.data.database.entities.UserEntity

/**
 * Reads never return an expired row: the caller passes the current time and the query filters on
 * it, so a stale row behaves exactly like a missing one and the repository goes to the network.
 */

@Dao
interface UserDao {

    @Upsert
    suspend fun save(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id AND expiresAt > :now")
    suspend fun get(id: String, now: Long): UserEntity?

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM users WHERE expiresAt <= :now")
    suspend fun deleteExpired(now: Long)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

}

@Dao
interface PostDao {

    @Upsert
    suspend fun save(post: PostEntity)

    @Query("SELECT * FROM posts WHERE id = :id AND expiresAt > :now")
    suspend fun get(id: String, now: Long): PostEntity?

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM posts WHERE expiresAt <= :now")
    suspend fun deleteExpired(now: Long)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

}
