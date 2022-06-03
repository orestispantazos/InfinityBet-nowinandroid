package com.infinitybet.premierleague.core.data.repository

import com.infinitybet.premierleague.core.data.Syncable
import com.infinitybet.premierleague.core.model.data.Author
import kotlinx.coroutines.flow.Flow

interface AuthorsRepository : Syncable {
    /**
     * Gets the available Authors as a stream
     */
    fun getAuthorsStream(): Flow<List<Author>>

    /**
     * Gets data for a specific author
     */
    fun getAuthorStream(id: String): Flow<Author>

    /**
     * Sets the user's currently followed authors
     */
    suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>)

    /**
     * Toggles the user's newly followed/unfollowed author
     */
    suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean)

    /**
     * Returns the users currently followed authors
     */
    fun getFollowedAuthorIdsStream(): Flow<Set<String>>
}
