package com.extopy.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
// On Kotlin/Native, `Dispatchers.IO` is an extension property and needs importing by name —
// without this, `IO` resolves to an internal object and the build fails.
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import kotlin.coroutines.CoroutineContext

// SQLite calls block the thread they run on, so they belong on the dispatcher meant for that rather
// than on Default, which is sized for the number of cores and would starve under a burst of queries.
actual val databaseDispatcher: CoroutineContext = Dispatchers.IO

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
