package com.extopy.domain.models.application

/**
 * Which deployment of Extopy this build talks to.
 *
 * Only *which* one: the URL that maps to is a data-layer concern, and [LOCAL] does not even have a
 * fixed one — it depends on how the device reaches the machine the backend runs on.
 */
enum class ExtopyEnvironment {

    /** extopy.com. */
    PRODUCTION,

    /** extopy.dev, the deployed staging environment. */
    DEVELOPMENT,

    /**
     * A backend running on the developer's own machine.
     *
     * Never shipped: the Android `local` flavor and an iOS debug build told to use it are the only
     * two ways to end up here.
     */
    LOCAL,

}
