package com.infinitybet.premierleague.core.data.repository

import com.infinitybet.premierleague.core.data.Synchronizer
import com.infinitybet.premierleague.core.data.changeListSync
import com.infinitybet.premierleague.core.data.model.asEntity
import com.infinitybet.premierleague.core.data.model.authorCrossReferences
import com.infinitybet.premierleague.core.data.model.authorEntityShells
import com.infinitybet.premierleague.core.data.model.episodeEntityShell
import com.infinitybet.premierleague.core.data.model.topicCrossReferences
import com.infinitybet.premierleague.core.data.model.topicEntityShells
import com.infinitybet.premierleague.core.database.dao.AuthorDao
import com.infinitybet.premierleague.core.database.dao.EpisodeDao
import com.infinitybet.premierleague.core.database.dao.NewsResourceDao
import com.infinitybet.premierleague.core.database.dao.TopicDao
import com.infinitybet.premierleague.core.database.model.AuthorEntity
import com.infinitybet.premierleague.core.database.model.EpisodeEntity
import com.infinitybet.premierleague.core.database.model.PopulatedNewsResource
import com.infinitybet.premierleague.core.database.model.TopicEntity
import com.infinitybet.premierleague.core.database.model.asExternalModel
import com.infinitybet.premierleague.core.datastore.ChangeListVersions
import com.infinitybet.premierleague.core.model.data.NewsResource
import com.infinitybet.premierleague.core.network.NiaNetwork
import com.infinitybet.premierleague.core.network.model.NetworkNewsResource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Disk storage backed implementation of the [NewsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstNewsRepository @Inject constructor(
    private val newsResourceDao: NewsResourceDao,
    private val episodeDao: EpisodeDao,
    private val authorDao: AuthorDao,
    private val topicDao: TopicDao,
    private val network: NiaNetwork,
) : NewsRepository {

    override fun getNewsResourcesStream(): Flow<List<NewsResource>> =
        newsResourceDao.getNewsResourcesStream()
            .map { it.map(PopulatedNewsResource::asExternalModel) }

    override fun getNewsResourcesStream(
        filterAuthorIds: Set<String>,
        filterTopicIds: Set<String>
    ): Flow<List<NewsResource>> = newsResourceDao.getNewsResourcesStream(
        filterAuthorIds = filterAuthorIds,
        filterTopicIds = filterTopicIds
    )
        .map { it.map(PopulatedNewsResource::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::newsResourceVersion,
            changeListFetcher = { currentVersion ->
                network.getNewsResourceChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(newsResourceVersion = latestVersion)
            },
            modelDeleter = newsResourceDao::deleteNewsResources,
            modelUpdater = { changedIds ->
                val networkNewsResources = network.getNewsResources(ids = changedIds)

                // Order of invocation matters to satisfy id and foreign key constraints!

                topicDao.insertOrIgnoreTopics(
                    topicEntities = networkNewsResources
                        .map(NetworkNewsResource::topicEntityShells)
                        .flatten()
                        .distinctBy(TopicEntity::id)
                )
                authorDao.insertOrIgnoreAuthors(
                    authorEntities = networkNewsResources
                        .map(NetworkNewsResource::authorEntityShells)
                        .flatten()
                        .distinctBy(AuthorEntity::id)
                )
                episodeDao.insertOrIgnoreEpisodes(
                    episodeEntities = networkNewsResources
                        .map(NetworkNewsResource::episodeEntityShell)
                        .distinctBy(EpisodeEntity::id)
                )
                newsResourceDao.upsertNewsResources(
                    newsResourceEntities = networkNewsResources
                        .map(NetworkNewsResource::asEntity)
                )
                newsResourceDao.insertOrIgnoreTopicCrossRefEntities(
                    newsResourceTopicCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::topicCrossReferences)
                        .distinct()
                        .flatten()
                )
                newsResourceDao.insertOrIgnoreAuthorCrossRefEntities(
                    newsResourceAuthorCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::authorCrossReferences)
                        .distinct()
                        .flatten()
                )
            }
        )
}
