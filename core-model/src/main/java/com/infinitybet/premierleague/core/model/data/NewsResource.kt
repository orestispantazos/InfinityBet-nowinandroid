package com.infinitybet.premierleague.core.model.data

import kotlinx.datetime.Instant

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class NewsResource(
    val id: String,
    val episodeId: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: NewsResourceType,
    val authors: List<Author>,
    val topics: List<Topic>
)
