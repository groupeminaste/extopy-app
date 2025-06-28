package com.extopy.models.navigation

object ExternalUriHandler {

    // Storage for when a URI arrives before the listener is set up
    private var cached: String? = null

    var listener: ((uri: String) -> Unit)? = null
        set(value) {
            println("ExternalUriHandler.listener: $value")
            field = value
            if (value != null) {
                // When a listener is set and `cached` is not empty,
                // immediately invoke the listener with the cached URI
                println("ExternalUriHandler.listener: invoking cached value: $cached")
                cached?.let { value.invoke(it) }
                cached = null
            }
        }

    // When a new URI arrives, cache it.
    // If the listener is already set, invoke it and clear the cache immediately.
    fun onNewUri(uri: String) {
        println("ExternalUriHandler.onNewUri: $uri")
        cached = uri
        listener?.let {
            println("ExternalUriHandler.onNewUri: invoking listener")
            it.invoke(uri)
            cached = null
        }
    }

}
