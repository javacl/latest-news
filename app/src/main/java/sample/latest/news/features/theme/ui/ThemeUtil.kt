package sample.latest.news.features.theme.ui

import sample.latest.news.R

object ThemeUtil {

    fun getThemeName(value: Int?): Int {
        return when (value) {
            ThemeType.Dark.value -> R.string.label_dark
            ThemeType.Light.value -> R.string.label_light
            else -> R.string.label_system_default
        }
    }
}
