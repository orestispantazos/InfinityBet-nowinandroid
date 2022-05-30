package com.google.samples.apps.nowinandroid.core.data.model

import com.google.samples.apps.nowinandroid.core.database.model.PredictionEntity
import com.google.samples.apps.nowinandroid.core.network.model.NetworkPrediction

fun NetworkPrediction.asEntity() = PredictionEntity(
    id = id,
    matchName = matchName,
    matchDate = matchDate,
    status = status,
    team1 = team1,
    team2 = team2,
    uniqueTip = uniqueTip,
)
