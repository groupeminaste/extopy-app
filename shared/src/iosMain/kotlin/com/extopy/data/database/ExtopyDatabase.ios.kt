package com.extopy.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import kotlin.coroutines.CoroutineContext

// Dispatchers.IO is not available on Kotlin/Native; Default is appropriate for SQLite access here.
actual val databaseDispatcher: CoroutineContext = Dispatchers.Default

fun getDatabaseBuilder(): RoomDatabase.Builder<ExtopyDatabase> {
    val dbFilePath = documentDirectory() + "/" + ExtopyDatabase.FILE_NAME
    return Room.databaseBuilder<ExtopyDatabase>(name = dbFilePath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
