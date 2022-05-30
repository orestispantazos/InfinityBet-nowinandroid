package com.google.samples.apps.nowinandroid.core.network.model

import com.google.samples.apps.nowinandroid.core.model.data.Episode
import com.google.samples.apps.nowinandroid.core.network.model.util.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Network representation of [Episode] when fetched from /episodes
 */
@Serializable
data class NetworkEpisode(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String?,
    val alternateAudio: String?,
    val newsResources: List<String> = listOf(),
    val authors: List<String> = listOf(),
)

/**
 * Network representation of [Episode] when fetched from /episodes/{id}
 */
@Serializable
data class NetworkEpisodeExpanded(
    val id: String,
    val name: String,
    @Serializable(InstantSerializer::class)
    val publishDate: Instant,
    val alternateVideo: String,
    val alternateAudio: String,
    val newsResources: List<NetworkNewsResource> = listOf(),
    val authors: List<NetworkAuthor> = listOf(),
)
