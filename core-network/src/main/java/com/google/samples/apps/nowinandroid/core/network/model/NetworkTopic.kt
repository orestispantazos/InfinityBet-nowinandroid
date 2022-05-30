package com.google.samples.apps.nowinandroid.core.network.model

import com.google.samples.apps.nowinandroid.core.model.data.Topic
import kotlinx.serialization.Serializable

/**
 * Network representation of [Topic]
 */
@Serializable
data class NetworkTopic(
    val id: String,
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val followed: Boolean = false,
)
