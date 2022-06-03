package com.infinitybet.premierleague.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

/**
 * Defines an NiA episode.
 * It is a parent in a 1 to many relationship with [NewsResourceEntity]
 */
@Entity(
    tableName = "episodes",
)
data class EpisodeEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(name = "publish_date")
    val publishDate: Instant,
    @ColumnInfo(name = "alternate_video")
    val alternateVideo: String?,
    @ColumnInfo(name = "alternate_audio")
    val alternateAudio: String?,
)
