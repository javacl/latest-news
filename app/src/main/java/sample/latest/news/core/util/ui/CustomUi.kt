package sample.latest.news.core.util.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sample.latest.news.R
import sample.latest.news.core.theme.ExtraLargeRadius
import sample.latest.news.core.theme.LargeRadius
import sample.latest.news.core.theme.SmallRadius
import sample.latest.news.core.theme.disable
import sample.latest.news.core.theme.divider
import sample.latest.news.core.theme.onDisable
import sample.latest.news.core.theme.w300
import sample.latest.news.core.theme.w400
import sample.latest.news.core.theme.w500
import sample.latest.news.core.theme.w700
import sample.latest.news.core.theme.x1
import sample.latest.news.core.theme.x2
import sample.latest.news.core.util.extensions.appShadow
import sample.latest.news.core.util.snackBar.AppSnackBarHost

val DividerThickness = 0.2.dp

@Composable
fun AppDivider(
    modifier: Modifier = Modifier
) = Divider(
    modifier = modifier,
    color = MaterialTheme.colorScheme.divider,
    thickness = DividerThickness
)

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    spacing: Dp = 0.dp
) = AppDivider(
    modifier = modifier.padding(start = spacing, end = spacing)
)

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    end: Dp = 0.dp,
    start: Dp = 0.dp
) = AppDivider(
    modifier = modifier.padding(top = top, bottom = bottom, start = start, end = end)
)

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    vertical: Dp = 0.dp,
    horizontal: Dp = 0.dp
) = AppDivider(
    modifier = modifier.padding(
        top = vertical,
        bottom = vertical,
        start = horizontal,
        end = horizontal
    )
)

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) = Box(
    modifier = modifier
        .appShadow(cornersRadius = SmallRadius)
        .clip(shape = shape)
        .background(color = backgroundColor)
) {
    content()
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    isWrap: Boolean = false,
    wrapPadding: Dp = if (isWrap) 32.dp else 0.dp,
    isBorder: Boolean = false,
    borderWidth: Dp = 1.dp,
    enabled: Boolean = true,
    loading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.disable,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onDisable,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    text: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.w700.x2,
    iconResource: Int? = null,
    iconSize: Dp = 16.dp,
    iconPadding: Dp = if (text.isNullOrEmpty()) 0.dp else 8.dp,
    onClick: () -> Unit
) {
    val isEnabled = enabled && !loading

    val mBackgroundColor = if (isEnabled) backgroundColor else disabledBackgroundColor

    val mContentColor = if (isEnabled) {

        if (isBorder) backgroundColor else contentColor

    } else {

        disabledContentColor
    }

    Row(
        modifier = modifier
            .then(
                if (isWrap) {
                    Modifier.wrapContentWidth()
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .height(height)
            .appShadow(cornersRadius = LargeRadius)
            .clip(shape)
            .then(
                if (isBorder) {
                    Modifier.border(
                        width = borderWidth,
                        color = mBackgroundColor,
                        shape = shape
                    )
                } else {
                    Modifier.background(mBackgroundColor)
                }
            )
            .clickable(
                onClick = onClick,
                enabled = isEnabled
            )
            .padding(horizontal = wrapPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (loading) {

            AppCircularProgressIndicator(
                color = mContentColor
            )

        } else {

            iconResource?.let {
                Icon(
                    modifier = Modifier
                        .padding(end = iconPadding)
                        .size(iconSize),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = mContentColor
                )
            }

            text?.let {
                Text(
                    text = it,
                    style = textStyle,
                    color = mContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) = CircularProgressIndicator(
    modifier = modifier.size(16.dp),
    color = color,
    strokeWidth = 2.dp
)

@Composable
fun AppBottomSheetColumn(
    modifier: Modifier = Modifier,
    fullExpand: Boolean = false,
    fraction: Float = 0.9f,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (fullExpand) {
                    Modifier.wrapContentHeight()
                } else {
                    Modifier
                        .wrapContentHeight()
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * fraction)
                }
            )
    ) {

        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
                .wrapContentSize()
                .width(32.dp)
                .height(3.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.disable)
        )

        content()
    }
}

@Composable
fun AppBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    hostState: SnackbarHostState,
    onRefresh: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        content()

        AppSnackBarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            onRefresh = onRefresh
        )
    }
}

@Composable
fun AppIconButton(
    iconResource: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isSmall: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .wrapContentSize()
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false, radius = ExtraLargeRadius),
            onClick = onClick
        )
        .padding(if (isSmall) 8.dp else 16.dp),
    contentAlignment = Alignment.Center
) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(id = iconResource),
        contentDescription = null,
        tint = color
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppSwipeRefresh(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    ),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {

        content()

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            scale = true
        )
    }
}

@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable RowScope.() -> Unit
) = Row(
    modifier
        .appShadow()
        .fillMaxWidth()
        .height(80.dp)
        .background(backgroundColor)
        .selectableGroup(),
    horizontalArrangement = Arrangement.SpaceBetween,
    content = content
)

@Composable
fun RowScope.AppBottomNavigationItem(
    selectedIcon: Int?,
    unSelectedIcon: Int?,
    label: Int?,
    selected: Boolean,
    selectedContentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    val ripple = rememberRipple(bounded = false, color = selectedContentColor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedIcon != null && unSelectedIcon != null && label != null) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (selected) selectedIcon else unSelectedIcon),
                contentDescription = stringResource(id = label),
                tint = if (selected) selectedContentColor else unselectedContentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(label),
                style = MaterialTheme.typography.w400.x1,
                color = if (selected) selectedContentColor else unselectedContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        } else {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_add),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun AppExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.w300.x2,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    collapsedMaxLine: Int = 3,
    showMoreText: String = "   ${stringResource(id = R.string.label_more)}",
    showMoreTextStyle: TextStyle = MaterialTheme.typography.w500.x2,
    showMoreTextColor: Color = MaterialTheme.colorScheme.primary,
    showLessText: String = "   ${stringResource(id = R.string.label_less)}",
    showLessTextStyle: TextStyle = showMoreTextStyle,
    showLessTextColor: Color = showMoreTextColor,
    textAlign: TextAlign? = null
) {
    var isExpanded by remember { mutableStateOf(false) }

    var clickable by remember { mutableStateOf(false) }

    var lastCharIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
            .then(modifier)
    ) {
        Text(
            modifier = textModifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(
                            style = showLessTextStyle.toSpanStyle().copy(
                                color = showLessTextColor
                            )
                        ) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(
                            style = showMoreTextStyle.toSpanStyle().copy(
                                color = showMoreTextColor
                            )
                        ) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                }
            },
            style = style,
            textAlign = textAlign,
            color = color
        )
    }
}
