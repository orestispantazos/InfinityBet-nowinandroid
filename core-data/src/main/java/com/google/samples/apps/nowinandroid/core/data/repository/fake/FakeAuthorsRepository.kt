package com.google.samples.apps.nowinandroid.core.data.repository.fake

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.repository.AuthorsRepository
import com.google.samples.apps.nowinandroid.core.datastore.NiaPreferences
import com.google.samples.apps.nowinandroid.core.model.data.Author
import com.google.samples.apps.nowinandroid.core.network.Dispatcher
import com.google.samples.apps.nowinandroid.core.network.NiaDispatchers.IO
import com.google.samples.apps.nowinandroid.core.network.fake.FakeDataSource
import com.google.samples.apps.nowinandroid.core.network.model.NetworkAuthor
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Fake implementation of the [AuthorsRepository] that returns hardcoded authors.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeAuthorsRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val niaPreferences: NiaPreferences,
    private val networkJson: Json,
) : AuthorsRepository {

    override fun getAuthorsStream(): Flow<List<Author>> = flow {
        emit(
            networkJson.decodeFromString<List<NetworkAuthor>>(FakeDataSource.authors).map {
                Author(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    twitter = it.twitter,
                    mediumPage = it.mediumPage,
                    bio = it.bio,
                )
            }
        )
    }
        .flowOn(ioDispatcher)

    override fun getAuthorStream(id: String): Flow<Author> {
        return getAuthorsStream().map { it.first { author -> author.id == id } }
    }

    override suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) {
        niaPreferences.setFollowedAuthorIds(followedAuthorIds)
    }

    override suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) {
        niaPreferences.toggleFollowedAuthorId(followedAuthorId, followed)
    }

    override fun getFollowedAuthorIdsStream(): Flow<Set<String>> = niaPreferences.followedAuthorIds

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}
