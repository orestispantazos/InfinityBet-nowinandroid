package com.infinitybet.premierleague.core.model.data

import kotlinx.datetime.Instant

/**
 * External data layer representation of an NiA episode
 */
data class Episode(
    val id: String,
    val name: String,
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val newsResources: List<NewsResource>,
    val authors: List<Author>
)
