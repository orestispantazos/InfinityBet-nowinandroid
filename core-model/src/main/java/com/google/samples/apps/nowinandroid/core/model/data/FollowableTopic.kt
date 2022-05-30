package com.google.samples.apps.nowinandroid.core.model.data

/**
 * A [topic] with the additional information for whether or not it is followed.
 */
data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean
)
