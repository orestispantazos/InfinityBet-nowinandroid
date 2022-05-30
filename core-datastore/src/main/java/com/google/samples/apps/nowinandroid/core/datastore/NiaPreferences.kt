package com.google.samples.apps.nowinandroid.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry

class NiaPreferences @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.followedTopicIds.clear()
                    this.followedTopicIds.addAll(followedTopicIds)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    val current =
                        if (followed) {
                            followedTopicIds + followedTopicId
                        } else {
                            followedTopicIds - followedTopicId
                        }
                    this.followedTopicIds.clear()
                    this.followedTopicIds.addAll(current)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    val followedTopicIds: Flow<Set<String>> = userPreferences.data
        .retry {
            Log.e("NiaPreferences", "Failed to read user preferences", it)
            true
        }
        .map { it.followedTopicIdsList.toSet() }

    suspend fun getChangeListVersions() = userPreferences.data
        .map {
            ChangeListVersions(
                topicVersion = it.topicChangeListVersion,
                authorVersion = it.authorChangeListVersion,
                episodeVersion = it.episodeChangeListVersion,
                newsResourceVersion = it.newsResourceChangeListVersion,
            )
        }
        .firstOrNull() ?: ChangeListVersions()

    /**
     * Update the [ChangeListVersions] using [update].
     */
    suspend fun updateChangeListVersion(update: ChangeListVersions.() -> ChangeListVersions) {
        try {
            userPreferences.updateData { currentPreferences ->
                val updatedChangeListVersions = update(
                    ChangeListVersions(
                        topicVersion = currentPreferences.topicChangeListVersion,
                        authorVersion = currentPreferences.authorChangeListVersion,
                        episodeVersion = currentPreferences.episodeChangeListVersion,
                        newsResourceVersion = currentPreferences.newsResourceChangeListVersion
                    )
                )

                currentPreferences.copy {
                    topicChangeListVersion = updatedChangeListVersions.topicVersion
                    authorChangeListVersion = updatedChangeListVersions.authorVersion
                    episodeChangeListVersion = updatedChangeListVersions.episodeVersion
                    newsResourceChangeListVersion = updatedChangeListVersions.newsResourceVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setFollowedAuthorIds(followedAuthorIds: Set<String>) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.followedAuthorIds.clear()
                    this.followedAuthorIds.addAll(followedAuthorIds)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setFollowedPredictionIds(followedPredictionIds: Set<String>) {
        try {
            userPreferences.updateData {
                it.copy {
                    this.followedAuthorIds.clear()
                    this.followedAuthorIds.addAll(followedAuthorIds)
//                    this.followedPredictionIds.clear()
//                    this.followedPredictionIds.addAll(followedPredictionIds)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun toggleFollowedAuthorId(followedAuthorId: String, followed: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    val current =
                        if (followed) {
                            followedAuthorIds + followedAuthorId
                        } else {
                            followedAuthorIds - followedAuthorId
                        }
                    this.followedAuthorIds.clear()
                    this.followedAuthorIds.addAll(current)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun toggleFollowedPredictionId(followedPredictionId: String, followed: Boolean) {
        try {
            userPreferences.updateData {
                it.copy {
                    val current =
                        if (followed) {
                            followedAuthorIds + followedPredictionId
                        } else {
                            followedAuthorIds - followedPredictionId
                        }
//                        if (followed) {
//                            followedPredictionIds + followedPredictionId
//                        } else {
//                            followedPredictionIds - followedPredictionId
//                        }
                    this.followedAuthorIds.clear()
                    this.followedAuthorIds.addAll(current)
//                    this.followedPredictionIds.clear()
//                    this.followedPredictionIds.addAll(current)
                }
            }
        } catch (ioException: IOException) {
            Log.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    val followedAuthorIds: Flow<Set<String>> = userPreferences.data
        .retry {
            Log.e("NiaPreferences", "Failed to read user preferences", it)
            true
        }
        .map { it.followedAuthorIdsList.toSet() }

    val followedPredictionIds: Flow<Set<String>> = userPreferences.data
        .retry {
            Log.e("NiaPreferences", "Failed to read user preferences", it)
            true
        }
        .map { it.followedAuthorIdsList.toSet() }
        // .map { it.followedPredictionIdsList.toSet() }
}
