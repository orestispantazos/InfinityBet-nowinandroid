package com.google.samples.apps.nowinandroid.core.network.retrofit

import com.google.samples.apps.nowinandroid.core.network.BuildConfig
import com.google.samples.apps.nowinandroid.core.network.NiaNetwork
import com.google.samples.apps.nowinandroid.core.network.model.NetworkAuthor
import com.google.samples.apps.nowinandroid.core.network.model.NetworkChangeList
import com.google.samples.apps.nowinandroid.core.network.model.NetworkNewsResource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkPrediction
import com.google.samples.apps.nowinandroid.core.network.model.NetworkTopic
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitNiaNetworkApi {
    @GET(value = "topics")
    suspend fun getTopics(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkTopic>>

    @GET(value = "authors")
    suspend fun getAuthors(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkAuthor>>

    @GET(value = "predictions")
    suspend fun getPredictions(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkPrediction>>

    @GET(value = "newsresources")
    suspend fun getNewsResources(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkNewsResource>>

    @GET(value = "changelists/topics")
    suspend fun getTopicChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    @GET(value = "changelists/authors")
    suspend fun getAuthorsChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    @GET(value = "changelists/predictions")
    suspend fun getPredictionsChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    @GET(value = "changelists/newsresources")
    suspend fun getNewsResourcesChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>
}

private const val NiaBaseUrl = BuildConfig.BACKEND_URL

/**
 * Wrapper for data provided from the [NiaBaseUrl]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T
)

/**
 * [Retrofit] backed [NiaNetwork]
 */
@Singleton
class RetrofitNiaNetwork @Inject constructor(
    networkJson: Json
) : NiaNetwork {

    private val networkApi = Retrofit.Builder()
        .baseUrl(NiaBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    // TODO: Decide logging logic
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RetrofitNiaNetworkApi::class.java)

    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        networkApi.getTopics(ids = ids).data

    override suspend fun getAuthors(ids: List<String>?): List<NetworkAuthor> =
        networkApi.getAuthors(ids = ids).data

    override suspend fun getPredictions(ids: List<String>?): List<NetworkPrediction> =
        networkApi.getPredictions(ids = ids).data

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        networkApi.getNewsResources(ids = ids).data

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getTopicChangeList(after = after)

    override suspend fun getAuthorChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getAuthorsChangeList(after = after)

    override suspend fun getPredictionChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getPredictionsChangeList(after = after)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getNewsResourcesChangeList(after = after)
}
