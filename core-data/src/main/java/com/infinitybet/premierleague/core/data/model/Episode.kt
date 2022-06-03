package com.infinitybet.premierleague.core.data.model

import com.infinitybet.premierleague.core.database.model.EpisodeEntity
import com.infinitybet.premierleague.core.network.model.NetworkEpisode
import com.infinitybet.premierleague.core.network.model.NetworkEpisodeExpanded

fun NetworkEpisode.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)

fun NetworkEpisodeExpanded.asEntity() = EpisodeEntity(
    id = id,
    name = name,
    publishDate = publishDate,
    alternateVideo = alternateVideo,
    alternateAudio = alternateAudio,
)
