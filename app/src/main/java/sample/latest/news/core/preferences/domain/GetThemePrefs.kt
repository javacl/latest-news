package sample.latest.news.core.preferences.domain

import sample.latest.news.core.preferences.PreferencesDataStore
import javax.inject.Inject

class GetThemePrefs @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) {
    operator fun invoke() = preferencesDataStore.getTheme
}
