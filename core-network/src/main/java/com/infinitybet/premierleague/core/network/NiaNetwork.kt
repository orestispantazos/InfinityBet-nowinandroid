package com.google.samples.apps.nowinandroid.core.network

import com.google.samples.apps.nowinandroid.core.network.model.NetworkAuthor
import com.google.samples.apps.nowinandroid.core.network.model.NetworkChangeList
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsResource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkPrediction
import com.google.samples.apps.nowinandroid.core.network.model.NetworkTopic

/**
 * Interface representing network calls to the NIA backend
 */
interface NiaNetwork {
    suspend fun getTopics(ids: List<String>? = null): List<NetworkTopic>

    suspend fun getAuthors(ids: List<String>? = null): List<NetworkAuthor>

    suspend fun getPredictions(ids: List<String>? = null): List<NetworkPrediction>

    suspend fun getNewsResources(ids: List<String>? = null): List<NetworkNewsResource>

    suspend fun getTopicChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getAuthorChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getPredictionChangeList(after: Int? = null): List<NetworkChangeList>

    suspend fun getNewsResourceChangeList(after: Int? = null): List<NetworkChangeList>
}
