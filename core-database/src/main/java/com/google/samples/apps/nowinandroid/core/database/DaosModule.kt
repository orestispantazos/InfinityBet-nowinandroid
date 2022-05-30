package com.google.samples.apps.nowinandroid.core.database

import com.google.samples.apps.nowinandroid.core.database.dao.AuthorDao
import com.google.samples.apps.nowinandroid.core.database.dao.EpisodeDao
import com.google.samples.apps.nowinandroid.core.database.dao.NewsResourceDao
import com.google.samples.apps.nowinandroid.core.database.dao.PredictionDao
import com.google.samples.apps.nowinandroid.core.database.dao.TopicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesAuthorDao(
        database: NiaDatabase,
    ): AuthorDao = database.authorDao()

    @Provides
    fun providesPredictionDao(
        database: NiaDatabase,
    ): PredictionDao = database.predictionDao()

    @Provides
    fun providesTopicsDao(
        database: NiaDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesEpisodeDao(
        database: NiaDatabase,
    ): EpisodeDao = database.episodeDao()

    @Provides
    fun providesNewsResourceDao(
        database: NiaDatabase,
    ): NewsResourceDao = database.newsResourceDao()
}
