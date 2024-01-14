package me.nathanfallet.extopy.database

class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    //private val dbQuery = database.appDatabaseQueries

}
