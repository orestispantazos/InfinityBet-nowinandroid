package com.infinitybet.premierleague.core.data.model

import com.infinitybet.premierleague.core.database.model.TopicEntity
import com.infinitybet.premierleague.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)
