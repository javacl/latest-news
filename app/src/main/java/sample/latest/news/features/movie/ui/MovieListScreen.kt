package sample.latest.news.features.movie.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sample.latest.news.R
import sample.latest.news.core.theme.isLight
import sample.latest.news.core.theme.w400
import sample.latest.news.core.theme.x3
import sample.latest.news.core.util.extensions.OnBottomReached
import sample.latest.news.core.util.extensions.PerPageSize
import sample.latest.news.core.util.extensions.parseServerErrorMessage
import sample.latest.news.core.util.navigation.NavigationRoutes
import sample.latest.news.core.util.navigation.safeNavigate
import sample.latest.news.core.util.snackBar.showAppSnackBar
import sample.latest.news.core.util.ui.AppBox
import sample.latest.news.core.util.ui.AppCard
import sample.latest.news.core.util.ui.AppCircularProgressIndicator
import sample.latest.news.core.util.ui.AppSwipeRefresh
import sample.latest.news.features.movie.data.entity.MovieEntity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel
) {
    val context = LocalContext.current

    val search by viewModel.search.collectAsState()

    val movieList by viewModel.movieList.collectAsState(initial = null)

    val snackBarHostState = remember { SnackbarHostState() }

    val networkViewState by viewModel.networkViewState.collectAsState()

    LaunchedEffect(networkViewState) {

        if (networkViewState.showError) {

            snackBarHostState.showAppSnackBar(
                message = context.parseServerErrorMessage(networkViewState),
                duration = SnackbarDuration.Short
            )
        }
    }

    val lazyGridState = rememberLazyGridState()

    val gridColumns = 2

    AppBox(
        hostState = snackBarHostState,
        onRefresh = viewModel::refresh
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            MovieListToolbarItem(
                navController = navController,
                viewModel = viewModel,
                search = search
            )

            AppSwipeRefresh(
                refreshing = networkViewState.showProgress,
                onRefresh = viewModel::refresh
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(gridColumns),
                    contentPadding = PaddingValues(8.dp),
                    state = lazyGridState
                ) {
                    items(
                        items = movieList ?: emptyList(),
                        key = { item -> item.id }
                    ) { item ->
                        MovieListItem(
                            context = context,
                            navController = navController,
                            item = item
                        )
                    }

                    if (networkViewState.showProgressMore) {

                        item(span = { GridItemSpan(gridColumns) }) {
                            MovieListProgressMoreItem()
                        }
                    }
                }
            }
        }
    }

    lazyGridState.OnBottomReached {

        if (!networkViewState.showProgress || !networkViewState.showProgressMore) {

            movieList?.let {
                if (it.size >= PerPageSize)
                    viewModel.getNextPage()
            }
        }
    }
}

@Composable
fun MovieListToolbarItem(
    navController: NavController,
    viewModel: MovieListViewModel,
    search: String
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    navController.safeNavigate(NavigationRoutes.ThemeList.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (MaterialTheme.colorScheme.isLight()) R.drawable.ic_dark_mode_off else R.drawable.ic_dark_mode_on),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            value = search,
            textStyle = MaterialTheme.typography.w400.x3.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            onValueChange = viewModel::setSearch,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        if (search.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.txt_search),
                                style = MaterialTheme.typography.w400.x3,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        innerTextField()
                    }
                }
            }
        )
    }
}

@Composable
fun MovieListItem(
    context: Context,
    navController: NavController,
    item: MovieEntity,
    width: Dp? = null
) {
    AppCard(
        modifier = Modifier
            .padding(8.dp)
            .then(
                if (width != null) {
                    Modifier.width(width)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .aspectRatio(3f / 4f)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(item.poster)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    val route = NavigationRoutes.Movie.route + "/${item.id}"
                    navController.safeNavigate(route)
                },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieListProgressMoreItem() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AppCircularProgressIndicator()
    }
}
