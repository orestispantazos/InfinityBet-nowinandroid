package com.google.samples.apps.nowinandroid.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.samples.apps.nowinandroid.core.model.data.Prediction

/**
 * Defines an author for either an [EpisodeEntity] or [NewsResourceEntity].
 * It has a many to many relationship with both entities
 */
@Entity(
    tableName = "predictions",
)
data class PredictionEntity(
    @PrimaryKey
    val id: String,
    val matchName: String,
    @ColumnInfo(name = "matchDate")
    val matchDate: String,
    @ColumnInfo(defaultValue = "")
    val status: String,
    @ColumnInfo(name = "team1", defaultValue = "")
    val team1: String,
    @ColumnInfo(name = "team2", defaultValue = "")
    val team2: String,
    @ColumnInfo(defaultValue = "")
    val uniqueTip: String,
)

fun PredictionEntity.asExternalModel() = Prediction(
    id = id,
    matchName = matchName,
    matchDate = matchDate,
    status = status,
    team1 = team1,
    team2 = team2,
    uniqueTip = uniqueTip,
)
