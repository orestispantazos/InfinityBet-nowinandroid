package com.google.samples.apps.nowinandroid.core.datastore

/**
 * Class summarizing the local version of each model for sync
 */
data class ChangeListVersions(
    val topicVersion: Int = -1,
    val authorVersion: Int = -1,
    val predictionVersion: Int = -1,
    val episodeVersion: Int = -1,
    val newsResourceVersion: Int = -1,
)
