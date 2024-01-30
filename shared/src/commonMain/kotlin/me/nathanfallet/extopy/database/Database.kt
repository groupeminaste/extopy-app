package me.nathanfallet.extopy.database

class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())

    val usersQueries = database.usersDatabaseQueries
    val postsQueries = database.postsDatabaseQueries

}
