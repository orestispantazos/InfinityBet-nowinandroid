package com.infinitybet.premierleague.core.data.model

import com.infinitybet.premierleague.core.database.model.PredictionEntity
import com.infinitybet.premierleague.core.network.model.NetworkPrediction

fun NetworkPrediction.asEntity() = PredictionEntity(
    id = id,
    matchName = matchName,
    matchDate = matchDate,
    status = status,
    team1 = team1,
    team2 = team2,
    uniqueTip = uniqueTip,
)
