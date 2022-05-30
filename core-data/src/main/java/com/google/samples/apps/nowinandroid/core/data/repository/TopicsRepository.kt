package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Syncable
import com.google.samples.apps.nowinandroid.core.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface TopicsRepository : Syncable {
    /**
     * Gets the available topics as a stream
     */
    fun getTopicsStream(): Flow<List<Topic>>

    /**
     * Gets data for a specific topic
     */
    fun getTopic(id: String): Flow<Topic>

    /**
     * Sets the user's currently followed topics
     */
    suspend fun setFollowedTopicIds(followedTopicIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed topic
     */
    suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean)

    /**
     * Returns the users currently followed topics
     */
    fun getFollowedTopicIdsStream(): Flow<Set<String>>
}
