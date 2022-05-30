package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.changeListSync
import com.google.samples.apps.nowinandroid.core.data.model.asEntity
import com.google.samples.apps.nowinandroid.core.database.dao.AuthorDao
import com.google.samples.apps.nowinandroid.core.database.model.AuthorEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.datastore.ChangeListVersions
import com.google.samples.apps.nowinandroid.core.datastore.NiaPreferences
import com.google.samples.apps.nowinandroid.core.model.data.Author
import com.google.samples.apps.nowinandroid.core.network.NiaNetwork
import com.google.samples.apps.nowinandroid.core.network.model.NetworkAuthor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [AuthorsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstAuthorsRepository @Inject constructor(
    private val authorDao: AuthorDao,
    private val network: NiaNetwork,
    private val niaPreferences: NiaPreferences,
) : AuthorsRepository {

    override fun getAuthorStream(id: String): Flow<Author> =
        authorDao.getAuthorEntityStream(id).map {
            it.asExternalModel()
        }

    override fun getAuthorsStream(): Flow<List<Author>> =
        authorDao.getAuthorEntitiesStream()
            .map { it.map(AuthorEntity::asExternalModel) }

    override suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) =
        niaPreferences.setFollowedAuthorIds(followedAuthorIds)

    override suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) =
        niaPreferences.toggleFollowedAuthorId(followedAuthorId, followed)

    override fun getFollowedAuthorIdsStream(): Flow<Set<String>> = niaPreferences.followedAuthorIds

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::authorVersion,
            changeListFetcher = { currentVersion ->
                network.getAuthorChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(authorVersion = latestVersion)
            },
            modelDeleter = authorDao::deleteAuthors,
            modelUpdater = { changedIds ->
                val networkAuthors = network.getAuthors(ids = changedIds)
                authorDao.upsertAuthors(
                    entities = networkAuthors.map(NetworkAuthor::asEntity)
                )
            }
        )
}
