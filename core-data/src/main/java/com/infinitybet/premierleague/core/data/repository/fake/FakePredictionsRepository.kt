package com.google.samples.apps.nowinandroid.core.data.repository.fake

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.repository.PredictionsRepository
import com.google.samples.apps.nowinandroid.core.datastore.NiaPreferences
import com.google.samples.apps.nowinandroid.core.model.data.Prediction
import com.infinitybet.premierleague.core.network.Dispatcher
import com.infinitybet.premierleague.core.network.NiaDispatchers.IO
import com.infinitybet.premierleague.core.network.fake.FakeDataSource
import com.infinitybet.premierleague.core.network.model.NetworkPrediction
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Fake implementation of the [PredictionsRepository] that returns hardcoded predictions.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakePredictionsRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val niaPreferences: NiaPreferences,
    private val networkJson: Json,
) : PredictionsRepository {

    override fun getPredictionsStream(): Flow<List<Prediction>> = flow {
        emit(
            networkJson.decodeFromString<List<NetworkPrediction>>(FakeDataSource.predictions).map {
                Prediction(
                    id = it.id,
                    matchName = it.matchName,
                    matchDate = it.matchDate,
                    status = it.status,
                    team1 = it.team1,
                    team2 = it.team2,
                    uniqueTip = it.uniqueTip,
                )
            }
        )
    }
        .flowOn(ioDispatcher)

    override fun getPredictionStream(id: String): Flow<Prediction> {
        return getPredictionsStream().map { it.first { prediction -> prediction.id == id } }
    }

    override suspend fun setFollowedPredictionIds(followedPredictionIds: Set<String>) {
        niaPreferences.setFollowedPredictionIds(followedPredictionIds)
    }

    override suspend fun toggleFollowedPredictionId(followedPredictionId: String, followed: Boolean) {
        niaPreferences.toggleFollowedPredictionId(followedPredictionId, followed)
    }

    override fun getFollowedPredictionIdsStream(): Flow<Set<String>> = niaPreferences.followedPredictionIds

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
