package me.nathanfallet.extopy.database

class Database(databaseDriverFactory: IDatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())

    val usersQueries = database.usersDatabaseQueries
    val postsQueries = database.postsDatabaseQueries

}
