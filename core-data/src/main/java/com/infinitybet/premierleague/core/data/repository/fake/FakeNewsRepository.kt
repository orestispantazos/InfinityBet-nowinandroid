package com.google.samples.apps.nowinandroid.core.data.repository.fake

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.model.asEntity
import com.google.samples.apps.nowinandroid.core.data.repository.NewsRepository
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.model.data.NewsResource
import com.infinitybet.premierleague.core.network.Dispatcher
import com.infinitybet.premierleague.core.network.NiaDispatchers.IO
import com.infinitybet.premierleague.core.network.fake.FakeDataSource
import com.infinitybet.premierleague.core.network.model.NetworkNewsResource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Fake implementation of the [NewsRepository] that retrieves the news resources from a JSON String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeNewsRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : NewsRepository {

    override fun getNewsResourcesStream(): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>,
    ): Flow<List<NewsResource>> =
        flow {
            emit(
                networkJson.decodeFromString<List<NetworkNewsResource>>(FakeDataSource.data)
                    .filter {
                        it.authors.intersect(filterAuthorIds).isNotEmpty() ||
                            it.topics.intersect(filterTopicIds).isNotEmpty()
                    }
                    .map(NetworkNewsResource::asEntity)
                    .map(NewsResourceEntity::asExternalModel)
            )
        }
            .flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
