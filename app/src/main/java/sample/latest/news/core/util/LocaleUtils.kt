package sample.latest.news.core.util

import android.content.Context
import java.util.Locale

fun localizedContext(baseContext: Context, locale: Locale = Locale("fa")): Context {
    Locale.setDefault(locale)
    val configuration = baseContext.resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    return baseContext.createConfigurationContext(configuration)
}
