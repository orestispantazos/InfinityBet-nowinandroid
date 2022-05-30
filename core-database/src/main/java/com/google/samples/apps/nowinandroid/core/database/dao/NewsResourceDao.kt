package com.google.samples.apps.nowinandroid.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceAuthorCrossRef
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceEntity
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceTopicCrossRef
import com.google.samples.apps.nowinandroid.core.database.model.PopulatedNewsResource
import com.google.samples.apps.nowinandroid.core.model.data.NewsResource
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [NewsResource] and [NewsResourceEntity] access
 */
@Dao
interface NewsResourceDao {
    @Query(
        value = """
            SELECT * FROM news_resources
            ORDER BY publish_date DESC
    """
    )
    fun getNewsResourcesStream(): Flow<List<PopulatedNewsResource>>

    @Query(
        value = """
            SELECT * FROM news_resources
            WHERE id in
            (
                SELECT news_resource_id FROM news_resources_topics
                WHERE topic_id IN (:filterTopicIds)
            )
            OR id in
            (
                SELECT news_resource_id FROM news_resources_authors
                WHERE author_id  IN (:filterAuthorIds)
            )
            ORDER BY publish_date DESC
    """
    )
    fun getNewsResourcesStream(
        filterAuthorIds: Set<String> = emptySet(),
        filterTopicIds: Set<String> = emptySet(),
    ): Flow<List<PopulatedNewsResource>>

    /**
     * Inserts [entities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreNewsResources(entities: List<NewsResourceEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateNewsResources(entities: List<NewsResourceEntity>)

    /**
     * Inserts or updates [newsResourceEntities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertNewsResources(newsResourceEntities: List<NewsResourceEntity>) = upsert(
        items = newsResourceEntities,
        insertMany = ::insertOrIgnoreNewsResources,
        updateMany = ::updateNewsResources
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopicCrossRefEntities(
        newsResourceTopicCrossReferences: List<NewsResourceTopicCrossRef>
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAuthorCrossRefEntities(
        newsResourceAuthorCrossReferences: List<NewsResourceAuthorCrossRef>
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM news_resources
            WHERE id in (:ids)
        """
    )
    suspend fun deleteNewsResources(ids: List<String>)
}
