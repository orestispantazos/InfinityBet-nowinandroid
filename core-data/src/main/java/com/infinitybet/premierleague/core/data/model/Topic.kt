package com.google.samples.apps.nowinandroid.core.data.model

import com.google.samples.apps.nowinandroid.core.database.model.TopicEntity
import com.infinitybet.premierleague.core.network.model.NetworkTopic

fun NetworkTopic.asEntity() = TopicEntity(
    id = id,
    name = name,
    shortDescription = shortDescription,
    longDescription = longDescription,
    url = url,
    imageUrl = imageUrl
)
