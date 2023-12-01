package sample.latest.news.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

private val darkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    scrim = md_theme_dark_scrim
)

private val lightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    scrim = md_theme_light_scrim
)

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5

val ColorScheme.warning: Color
    @Composable
    get() = if (isLight()) md_theme_light_warning else md_theme_dark_warning

val ColorScheme.onWarning: Color
    @Composable
    get() = if (isLight()) md_theme_light_onWarning else md_theme_dark_onWarning

val ColorScheme.success: Color
    @Composable
    get() = if (isLight()) md_theme_light_success else md_theme_dark_success

val ColorScheme.onSuccess: Color
    @Composable
    get() = if (isLight()) md_theme_light_onSuccess else md_theme_dark_onSuccess

val ColorScheme.disable: Color
    @Composable
    get() = if (isLight()) md_theme_light_disable else md_theme_dark_disable

val ColorScheme.onDisable: Color
    @Composable
    get() = if (isLight()) md_theme_light_onDisable else md_theme_dark_onDisable

val ColorScheme.divider: Color
    @Composable
    get() = if (isLight()) md_theme_light_divider else md_theme_dark_divider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
