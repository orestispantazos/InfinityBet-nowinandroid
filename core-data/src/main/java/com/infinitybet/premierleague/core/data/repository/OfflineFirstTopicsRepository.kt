package com.google.samples.apps.nowinandroid.core.data.repository

import com.google.samples.apps.nowinandroid.core.data.Synchronizer
import com.google.samples.apps.nowinandroid.core.data.changeListSync
import com.google.samples.apps.nowinandroid.core.data.model.asEntity
import com.google.samples.apps.nowinandroid.core.database.dao.TopicDao
import com.google.samples.apps.nowinandroid.core.database.model.TopicEntity
import com.google.samples.apps.nowinandroid.core.database.model.asExternalModel
import com.google.samples.apps.nowinandroid.core.datastore.ChangeListVersions
import com.google.samples.apps.nowinandroid.core.datastore.NiaPreferences
import com.google.samples.apps.nowinandroid.core.model.data.Topic
import com.infinitybet.premierleague.core.network.NiaNetwork
import com.infinitybet.premierleague.core.network.model.NetworkTopic
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [TopicsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstTopicsRepository @Inject constructor(
    private val topicDao: TopicDao,
    private val network: NiaNetwork,
    private val niaPreferences: NiaPreferences,
) : TopicsRepository {

    override fun getTopicsStream(): Flow<List<Topic>> =
        topicDao.getTopicEntitiesStream()
            .map { it.map(TopicEntity::asExternalModel) }

    override fun getTopic(id: String): Flow<Topic> =
        topicDao.getTopicEntity(id).map { it.asExternalModel() }

    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
        niaPreferences.setFollowedTopicIds(followedTopicIds)

    override suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) =
        niaPreferences.toggleFollowedTopicId(followedTopicId, followed)

    override fun getFollowedTopicIdsStream() = niaPreferences.followedTopicIds

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                network.getTopicChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = topicDao::deleteTopics,
            modelUpdater = { changedIds ->
                val networkTopics = network.getTopics(ids = changedIds)
                topicDao.upsertTopics(
                    entities = networkTopics.map(NetworkTopic::asEntity)
                )
            }
        )
}
