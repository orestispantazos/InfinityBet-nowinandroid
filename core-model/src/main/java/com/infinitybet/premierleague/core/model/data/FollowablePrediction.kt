package com.infinitybet.premierleague.core.model.data

/**
 * An [prediction] with the additional information for whether or not it is followed.
 */
data class FollowablePrediction(
    val prediction: Prediction,
    val isFollowed: Boolean
)
