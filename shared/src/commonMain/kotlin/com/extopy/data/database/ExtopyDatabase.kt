package com.extopy.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.database.entities.PostEntity
import com.extopy.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(ExtopyDatabaseConstructor::class)
abstract class ExtopyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        const val FILE_NAME = "extopy.db"
    }
}

// Room generates the actual implementations per platform via KSP.
@Suppress("KotlinNoActualForExpectError", "EXPECT_ACTUAL_INCOMPATIBLE_RETURN_TYPE")
expect object ExtopyDatabaseConstructor : RoomDatabaseConstructor<ExtopyDatabase> {
    override fun initialize(): ExtopyDatabase
}

/** Finalizes a platform-provided builder into a ready-to-use database. */
fun buildExtopyDatabase(
    builder: RoomDatabase.Builder<ExtopyDatabase>,
): ExtopyDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(databaseDispatcher)
    // Everything in here is a cache the network can rebuild, so a schema change has nothing worth
    // migrating — dropping the tables is both correct and cheaper than writing a migration.
    .fallbackToDestructiveMigration(dropAllTables = true)
    .build()
