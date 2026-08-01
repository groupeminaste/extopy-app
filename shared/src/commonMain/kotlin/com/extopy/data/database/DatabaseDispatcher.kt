package com.extopy.data.database

import kotlin.coroutines.CoroutineContext

/** Background dispatcher used by Room for query execution (IO on every platform). */
expect val databaseDispatcher: CoroutineContext
