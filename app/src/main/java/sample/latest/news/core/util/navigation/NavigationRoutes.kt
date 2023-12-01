package sample.latest.news.core.util.navigation

import sample.latest.news.R

sealed class NavigationRoutes(
    val route: String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null,
    val label: Int? = null
) {
    // Main
    data object Root : NavigationRoutes(
        route = "root"
    )

    data object Empty : NavigationRoutes(
        route = "empty"
    )

    // Theme
    data object ThemeList : NavigationRoutes(
        route = "theme_list"
    )

    // Home
    data object Home : NavigationRoutes(
        route = "home",
        selectedIcon = R.drawable.ic_baseline_home,
        unSelectedIcon = R.drawable.ic_outline_home,
        label = R.string.label_home
    )

    // Movie
    data object MovieList : NavigationRoutes(
        route = "movie_list",
        selectedIcon = R.drawable.ic_baseline_videocam,
        unSelectedIcon = R.drawable.ic_outline_videocam,
        label = R.string.label_movie
    )

    data object Movie : NavigationRoutes(
        route = "movie/{${NavigationKey.KEY_ID}}"
    )

    // Subscription
    data object Subscription : NavigationRoutes(
        route = "subscription",
        selectedIcon = R.drawable.ic_baseline_subscriptions,
        unSelectedIcon = R.drawable.ic_outline_subscriptions,
        label = R.string.label_subscription
    )

    // Library
    data object Library : NavigationRoutes(
        route = "library",
        selectedIcon = R.drawable.ic_baseline_video_library,
        unSelectedIcon = R.drawable.ic_outline_video_library,
        label = R.string.label_library
    )
}
