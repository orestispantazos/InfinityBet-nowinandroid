package com.infinitybet.premierleague.core.model.data

/**
 * An [author] with the additional information for whether or not it is followed.
 */
data class FollowableAuthor(
    val author: Author,
    val isFollowed: Boolean
)
