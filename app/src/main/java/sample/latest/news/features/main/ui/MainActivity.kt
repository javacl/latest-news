package sample.latest.news.features.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import sample.latest.news.core.theme.AppTheme
import sample.latest.news.core.util.accompanist.systemUiController.rememberSystemUiController
import sample.latest.news.core.util.ui.BaseActivity
import sample.latest.news.features.theme.ui.ThemeType

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val theme by viewModel.theme.collectAsState(initial = null)

            val isDarkTheme = when (theme) {
                ThemeType.Dark.value -> true
                ThemeType.Light.value -> false
                else -> isSystemInDarkTheme()
            }

            AppTheme(darkTheme = isDarkTheme) {

                val systemUiController = rememberSystemUiController()
                val backgroundColor = MaterialTheme.colorScheme.background

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor
                ) {
                    MainScreen()
                }
            }
        }
    }
}
