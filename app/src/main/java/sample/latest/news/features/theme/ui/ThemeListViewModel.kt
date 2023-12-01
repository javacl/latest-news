package sample.latest.news.features.theme.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import sample.latest.news.core.preferences.domain.DoUpdateThemePrefs
import sample.latest.news.core.preferences.domain.GetThemePrefs
import sample.latest.news.core.util.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeListViewModel @Inject constructor(
    getThemePrefs: GetThemePrefs,
    private val updateThemePrefs: DoUpdateThemePrefs
) : BaseViewModel() {

    val theme = getThemePrefs()
        .flowOn(Dispatchers.IO)

    fun updateTheme(value: Int) {
        runBlocking { updateThemePrefs(value) }
    }
}
