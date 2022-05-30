package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Syncable
import com.google.samples.apps.nowinandroid.core.model.data.NewsResource
import kotlinx.coroutines.flow.Flow

/**
 * Data layer implementation for [NewsResource]
 */
interface NewsRepository : Syncable {
    /**
     * Returns available news resources as a stream.
     */
    fun getNewsResourcesStream(): Flow<List<NewsResource>>

    /**
     * Returns available news resources as a stream filtered by authors or topics.
     */
    fun getNewsResourcesStream(
        filterAuthorIds: Set<String> = emptySet(),
        filterTopicIds: Set<String> = emptySet(),
    ): Flow<List<NewsResource>>
}
