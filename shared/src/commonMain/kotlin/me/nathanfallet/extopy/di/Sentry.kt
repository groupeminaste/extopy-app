package me.nathanfallet.extopy.di

import io.sentry.kotlin.multiplatform.Context
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.SentryOptions

fun initializeSentry(context: Context?) {
    val configuration: (SentryOptions) -> Unit = {
        it.dsn = "https://0c402f45f26b3288980c13a4ef5db57a@o4506105040470016.ingest.sentry.io/4506370995453953"
        // Add common configuration here
    }
    if (context != null) {
        Sentry.init(context, configuration)
    } else {
        Sentry.init(configuration)
    }
}
