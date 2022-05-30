package com.google.samples.apps.nowinandroid.core.network.fake

import com.google.samples.apps.nowinandroid.core.network.Dispatcher
import com.google.samples.apps.nowinandroid.core.network.NiaDispatchers.IO
import com.google.samples.apps.nowinandroid.core.network.NiaNetwork
import com.google.samples.apps.nowinandroid.core.network.model.NetworkAuthor
import com.google.samples.apps.nowinandroid.core.network.model.NetworkChangeList
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsResource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkPrediction
import com.google.samples.apps.nowinandroid.core.network.model.NetworkTopic
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * [NiaNetwork] implementation that provides static news resources to aid development
 */
class FakeNiaNetwork @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json
) : NiaNetwork {
    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.topicsData)
        }

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.data)
        }

    override suspend fun getAuthors(ids: List<String>?): List<NetworkAuthor> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.authors)
        }

    override suspend fun getPredictions(ids: List<String>?): List<NetworkPrediction> =
        withContext(ioDispatcher) {
            networkJson.decodeFromString(FakeDataSource.predictions)
        }

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        getTopics().mapToChangeList(NetworkTopic::id)

    override suspend fun getAuthorChangeList(after: Int?): List<NetworkChangeList> =
        getAuthors().mapToChangeList(NetworkAuthor::id)

    override suspend fun getPredictionChangeList(after: Int?): List<NetworkChangeList> =
        getPredictions().mapToChangeList(NetworkPrediction::id)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        getNewsResources().mapToChangeList(NetworkNewsResource::id)
}

/**
 * Converts a list of [T] to change list of all the items in it where [idGetter] defines the
 * [NetworkChangeList.id]
 */
private fun <T> List<T>.mapToChangeList(
    idGetter: (T) -> String
) = mapIndexed { index, item ->
    NetworkChangeList(
        id = idGetter(item),
        changeListVersion = index,
        isDelete = false,
    )
}
