package com.infinitybet.premierleague.core.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.infinitybet.premierleague.core.model.data.NewsResource

/**
 * Extension function for displaying a [List] of [NewsResourceCardExpanded] backed by a generic
 * [List] [T].
 *
 * [newsResourceMapper] maps type [T] to a [NewsResource]
 * [isBookmarkedMapper] maps type [T] to whether the [NewsResource] is bookmarked
 * [onToggleBookmark] defines the action invoked when a user wishes to bookmark an item
 * [onItemClick] optional parameter for action to be performed when the card is clicked. The
 * default action launches an intent matching the card.
 */
fun <T> LazyListScope.newsResourceCardItems(
    items: List<T>,
    newsResourceMapper: (item: T) -> NewsResource,
    isBookmarkedMapper: (item: T) -> Boolean,
    onToggleBookmark: (item: T) -> Unit,
    onItemClick: ((item: T) -> Unit)? = null,
    itemModifier: Modifier = Modifier,
) = items(
    items = items,
    key = { newsResourceMapper(it).id },
    itemContent = { item ->
        val newsResource = newsResourceMapper(item)
        val launchResourceIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(newsResource.url))
        val context = LocalContext.current

        NewsResourceCardExpanded(
            newsResource = newsResource,
            isBookmarked = isBookmarkedMapper(item),
            onToggleBookmark = { onToggleBookmark(item) },
            onClick = {
                when (onItemClick) {
                    null -> ContextCompat.startActivity(context, launchResourceIntent, null)
                    else -> onItemClick(item)
                }
            },
            modifier = itemModifier
        )
    },
)
