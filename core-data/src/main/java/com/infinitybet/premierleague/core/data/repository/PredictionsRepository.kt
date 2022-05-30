package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Syncable
import com.google.samples.apps.nowinandroid.core.model.data.Prediction
import kotlinx.coroutines.flow.Flow

interface PredictionsRepository : Syncable {
    /**
     * Gets the available Predictions as a stream
     */
    fun getPredictionsStream(): Flow<List<Prediction>>

    /**
     * Gets data for a specific prediction
     */
    fun getPredictionStream(id: String): Flow<Prediction>

    /**
     * Sets the user's currently followed predictions
     */
    suspend fun setFollowedPredictionIds(followedPredictionIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed prediction
     */
    suspend fun toggleFollowedPredictionId(followedPredictionId: String, followed: Boolean)

    /**
     * Returns the users currently followed predictions
     */
    fun getFollowedPredictionIdsStream(): Flow<Set<String>>
}
