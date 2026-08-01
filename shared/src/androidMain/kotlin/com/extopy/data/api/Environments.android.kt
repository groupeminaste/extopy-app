package com.extopy.data.api

/**
 * `10.0.2.2` is the address the Android emulator maps the host machine to — `localhost` inside the
 * emulator is the emulator itself.
 *
 * On a physical device this will not reach anything; point it at the machine's LAN address instead.
 */
actual val localBaseUrl: String = "http://10.0.2.2:$LOCAL_PORT"
