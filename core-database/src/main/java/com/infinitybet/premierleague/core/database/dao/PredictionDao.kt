package com.google.samples.apps.nowinandroid.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.samples.apps.nowinandroid.core.database.model.PredictionEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [PredictionEntity] access
 */
@Dao
interface PredictionDao {
    @Query(
        value = """
        SELECT * FROM predictions
        WHERE id = :predictionId
    """
    )
    fun getPredictionEntityStream(predictionId: String): Flow<PredictionEntity>

    @Query(value = "SELECT * FROM predictions")
    fun getPredictionEntitiesStream(): Flow<List<PredictionEntity>>

    /**
     * Inserts [predictionEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePredictions(predictionEntities: List<PredictionEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updatePredictions(entities: List<PredictionEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Transaction
    suspend fun upsertPredictions(entities: List<PredictionEntity>) = upsert(
        items = entities,
        insertMany = ::insertOrIgnorePredictions,
        updateMany = ::updatePredictions
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM predictions
            WHERE id in (:ids)
        """
    )
    suspend fun deletePredictions(ids: List<String>)
}
