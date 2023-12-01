package sample.latest.news.features.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import sample.latest.news.core.util.accompanist.navigationMaterial.ExperimentalMaterialNavigationApi
import sample.latest.news.core.util.accompanist.navigationMaterial.ModalBottomSheetLayout
import sample.latest.news.core.util.accompanist.navigationMaterial.bottomSheet
import sample.latest.news.core.util.accompanist.navigationMaterial.rememberBottomSheetNavigator
import sample.latest.news.core.util.navigation.NavigationRoutes
import sample.latest.news.core.util.navigation.safeNavigate
import sample.latest.news.core.util.ui.AppBottomNavigation
import sample.latest.news.core.util.ui.AppBottomNavigationItem
import sample.latest.news.features.home.ui.HomeScreen
import sample.latest.news.features.library.ui.LibraryScreen
import sample.latest.news.features.subscription.ui.SubscriptionScreen
import sample.latest.news.features.theme.ui.ThemeListScreen
import sample.latest.news.features.theme.ui.ThemeListViewModel
import sample.latest.news.features.movie.ui.MovieListScreen
import sample.latest.news.features.movie.ui.MovieListViewModel
import sample.latest.news.features.movie.ui.MovieScreen
import sample.latest.news.features.movie.ui.MovieViewModel

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen() {

    val bottomSheetNavigator = rememberBottomSheetNavigator()

    val navController = rememberNavController(bottomSheetNavigator)

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    var showBottomBar by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar = when (destination.route) {
            NavigationRoutes.Home.route, NavigationRoutes.MovieList.route, NavigationRoutes.Subscription.route, NavigationRoutes.Library.route -> true
            else -> false
        }
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetShape = MaterialTheme.shapes.large,
        scrimColor = MaterialTheme.colorScheme.scrim
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (showBottomBar) {
                    MainBottomNavigation(
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.MovieList.route,
                route = NavigationRoutes.Root.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(
                    route = NavigationRoutes.Home.route
                ) {
                    HomeScreen()
                }
                composable(
                    route = NavigationRoutes.MovieList.route
                ) {
                    val viewModel = hiltViewModel<MovieListViewModel>(it)
                    MovieListScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(
                    route = NavigationRoutes.Movie.route
                ) {
                    val viewModel = hiltViewModel<MovieViewModel>(it)
                    MovieScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                composable(
                    route = NavigationRoutes.Subscription.route
                ) {
                    SubscriptionScreen()
                }
                composable(
                    route = NavigationRoutes.Library.route
                ) {
                    LibraryScreen()
                }
                composable(
                    route = NavigationRoutes.Home.route
                ) {
                    HomeScreen()
                }
                bottomSheet(
                    route = NavigationRoutes.ThemeList.route
                ) {
                    val viewModel = hiltViewModel<ThemeListViewModel>(it)
                    ThemeListScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val items = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.MovieList,
        NavigationRoutes.Empty,
        NavigationRoutes.Subscription,
        NavigationRoutes.Library
    )

    AppBottomNavigation {

        items.forEach { screen ->

            AppBottomNavigationItem(
                selectedIcon = screen.selectedIcon,
                unSelectedIcon = screen.unSelectedIcon,
                label = screen.label,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            ) {
                if (screen.route != NavigationRoutes.Empty.route) {

                    navController.safeNavigate(screen.route) {
                        popUpTo(NavigationRoutes.Root.route) { inclusive = true }
                    }

                }
            }
        }
    }
}
