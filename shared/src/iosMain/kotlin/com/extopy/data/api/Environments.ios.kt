package com.extopy.data.api

/** The iOS simulator shares the host's network stack, so the host is simply localhost. */
actual val localBaseUrl: String = "http://localhost:$LOCAL_PORT"
