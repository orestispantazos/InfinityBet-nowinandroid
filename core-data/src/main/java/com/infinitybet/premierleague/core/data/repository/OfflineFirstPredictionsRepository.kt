package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.changeListSync
import com.google.samples.apps.nowinandroid.core.data.model.asEntity
import com.google.samples.apps.nowinandroid.core.database.dao.PredictionDao
import com.google.samples.apps.nowinandroid.core.database.model.PredictionEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.datastore.ChangeListVersions
import com.google.samples.apps.nowinandroid.core.datastore.NiaPreferences
import com.google.samples.apps.nowinandroid.core.model.data.Prediction
import com.infinitybet.premierleague.core.network.NiaNetwork
import com.infinitybet.premierleague.core.network.model.NetworkPrediction
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [PredictionsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstPredictionsRepository @Inject constructor(
    private val predictionDao: PredictionDao,
    private val network: NiaNetwork,
    private val niaPreferences: NiaPreferences,
) : PredictionsRepository {

    override fun getPredictionStream(id: String): Flow<Prediction> =
        predictionDao.getPredictionEntityStream(id).map {
            it.asExternalModel()
        }

    override fun getPredictionsStream(): Flow<List<Prediction>> =
        predictionDao.getPredictionEntitiesStream()
            .map { it.map(PredictionEntity::asExternalModel) }

    override suspend fun setFollowedPredictionIds(followedPredictionIds: Set<String>) =
        niaPreferences.setFollowedPredictionIds(followedPredictionIds)

    override suspend fun toggleFollowedPredictionId(followedPredictionId: String, followed: Boolean) =
        niaPreferences.toggleFollowedPredictionId(followedPredictionId, followed)

    override fun getFollowedPredictionIdsStream(): Flow<Set<String>> = niaPreferences.followedPredictionIds

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::predictionVersion,
            changeListFetcher = { currentVersion ->
                network.getPredictionChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(predictionVersion = latestVersion)
            },
            modelDeleter = predictionDao::deletePredictions,
            modelUpdater = { changedIds ->
                val networkPredictions = network.getPredictions(ids = changedIds)
                predictionDao.upsertPredictions(
                    entities = networkPredictions.map(NetworkPrediction::asEntity)
                )
            }
        )
}
