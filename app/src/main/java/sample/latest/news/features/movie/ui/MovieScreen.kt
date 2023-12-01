package sample.latest.news.features.movie.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sample.latest.news.R
import sample.latest.news.core.theme.w300
import sample.latest.news.core.theme.w400
import sample.latest.news.core.theme.w500
import sample.latest.news.core.theme.w900
import sample.latest.news.core.theme.x1
import sample.latest.news.core.theme.x2
import sample.latest.news.core.theme.x3
import sample.latest.news.core.theme.x4
import sample.latest.news.core.util.ui.AppButton
import sample.latest.news.core.util.ui.AppDivider
import sample.latest.news.core.util.ui.AppExpandableText
import sample.latest.news.core.util.ui.AppIconButton
import sample.latest.news.core.util.ui.DividerThickness
import sample.latest.news.features.movie.data.entity.MovieEntity

@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel
) {
    val context = LocalContext.current

    val movie by viewModel.movie.collectAsState(initial = null)

    val movieSimilarList by viewModel.movieSimilarList.collectAsState(initial = null)

    val showFullInfo by viewModel.showFullInfo.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        movie?.let { item ->

            MovieVideoItem(
                context = context,
                navController = navController,
                item = item
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                item {
                    MovieInfoItem(
                        viewModel = viewModel,
                        item = item,
                        showFullInfo = showFullInfo
                    )
                }

                item {
                    MovieOptionListItem()
                }

                item {
                    MovieDescItem(
                        item = item
                    )
                }

                item {
                    MovieSubscribeItem(
                        context = context
                    )
                }

                item {
                    MovieAddCommentItem(
                        context = context
                    )
                }

                item {
                    MovieSimilarListItem(
                        context = context,
                        navController = navController,
                        movieSimilarList = movieSimilarList
                    )
                }
            }
        }
    }
}

@Composable
fun MovieVideoItem(
    context: Context,
    navController: NavController,
    item: MovieEntity
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(item.poster)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp),
            painter = painterResource(id = R.drawable.ic_baseline_play_circle_outline),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )

        AppIconButton(
            modifier = Modifier.align(Alignment.TopStart),
            iconResource = R.drawable.ic_arrow_back,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            navController.navigateUp()
        }
    }

    AppDivider()
}

@Composable
fun MovieInfoItem(
    viewModel: MovieViewModel,
    item: MovieEntity,
    showFullInfo: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = viewModel::toggleShowFullInfo)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = item.name,
                style = MaterialTheme.typography.w900.x4,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (showFullInfo) R.drawable.ic_baseline_keyboard_arrow_up else R.drawable.ic_baseline_keyboard_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        AnimatedVisibility(
            visible = showFullInfo
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = item.engName,
                style = MaterialTheme.typography.w500.x2,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.txt_fake_view_and_date),
            style = MaterialTheme.typography.w300.x1,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun MovieOptionListItem() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        // Like
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable { }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_thumb_up),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(id = R.string.txt_fake_like_numbers),
                        style = MaterialTheme.typography.w400.x1,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(DividerThickness)
                        .background(MaterialTheme.colorScheme.onSurface)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable { }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_thumb_down),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Share
        item {
            MovieOptionItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                iconResource = R.drawable.ic_share,
                textResource = R.string.label_share
            ) {}
        }

        // Download
        item {
            MovieOptionItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                iconResource = R.drawable.ic_cloud_download,
                textResource = R.string.label_download
            ) {}
        }

        // Save
        item {
            MovieOptionItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                iconResource = R.drawable.ic_save,
                textResource = R.string.label_save
            ) {}
        }
    }
}

@Composable
fun MovieOptionItem(
    modifier: Modifier = Modifier,
    iconResource: Int,
    textResource: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = textResource),
            style = MaterialTheme.typography.w400.x1,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MovieDescItem(
    item: MovieEntity
) {
    AppExpandableText(
        modifier = Modifier.padding(16.dp),
        text = item.story
    )
}

@Composable
fun MovieSubscribeItem(
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(stringResource(id = R.string.link_latest_news_logo))
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.label_latest_news),
                style = MaterialTheme.typography.w900.x3,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.txt_fake_subscriber),
                style = MaterialTheme.typography.w300.x1,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        AppButton(
            text = stringResource(id = R.string.label_subscribe),
            isWrap = true,
            wrapPadding = 16.dp,
            height = 32.dp,
            textStyle = MaterialTheme.typography.w500.x2
        ) {}
    }

    AppDivider(top = 16.dp, end = 16.dp, start = 16.dp)
}

@Composable
fun MovieAddCommentItem(
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.txt_fake_comments),
                style = MaterialTheme.typography.w300.x2,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(stringResource(id = R.string.link_avatar))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.txt_write_comment),
                    style = MaterialTheme.typography.w300.x2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun MovieSimilarListItem(
    context: Context,
    navController: NavController,
    movieSimilarList: List<MovieEntity>?
) {
    Text(
        modifier = Modifier.padding(top = 16.dp, end = 16.dp, start = 16.dp),
        text = stringResource(id = R.string.label_similar_items),
        style = MaterialTheme.typography.w900.x3,
        color = MaterialTheme.colorScheme.onBackground
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            items = movieSimilarList ?: emptyList(),
            key = { item -> item.id }
        ) { item ->
            MovieListItem(
                context = context,
                navController = navController,
                item = item,
                width = 152.dp
            )
        }
    }
}
