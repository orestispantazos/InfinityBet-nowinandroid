package com.google.samples.apps.nowinandroid.core.model.data

/**
 * External data layer representation of a Prediction
 */
data class Prediction(
    val id: String,
    val matchName: String,
    val matchDate: String,
    val status: String,
    val team1: String,
    val team2: String,
    val uniqueTip: String,
)
