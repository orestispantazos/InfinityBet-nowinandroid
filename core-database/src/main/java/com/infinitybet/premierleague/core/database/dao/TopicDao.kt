package com.google.samples.apps.nowinandroid.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.samples.apps.nowinandroid.core.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [TopicEntity] access
 */
@Dao
interface TopicDao {
    @Query(
        value = """
        SELECT * FROM topics
        WHERE id = :topicId
    """
    )
    fun getTopicEntity(topicId: String): Flow<TopicEntity>

    @Query(value = "SELECT * FROM topics")
    fun getTopicEntitiesStream(): Flow<List<TopicEntity>>

    @Query(
        value = """
        SELECT * FROM topics
        WHERE id IN (:ids)
    """
    )
    fun getTopicEntitiesStream(ids: Set<String>): Flow<List<TopicEntity>>

    /**
     * Inserts [topicEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreTopics(topicEntities: List<TopicEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateTopics(entities: List<TopicEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertTopics(entities: List<TopicEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnoreTopics,
        updateMany = ::updateTopics
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM topics
            WHERE id in (:ids)
        """
    )
    suspend fun deleteTopics(ids: List<String>)
}
