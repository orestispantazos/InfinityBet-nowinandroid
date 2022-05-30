package com.google.samples.apps.nowinandroid.core.network.model

import com.google.samples.apps.nowinandroid.core.model.data.Prediction
import kotlinx.serialization.Serializable

/**
 * Network representation of [Prediction]
 */
@Serializable
data class NetworkPrediction(
    val id: String,
    val matchName: String,
    val matchDate: String,
    val status: String,
    val team1: String,
    val team2: String,
    val uniqueTip: String,
)
