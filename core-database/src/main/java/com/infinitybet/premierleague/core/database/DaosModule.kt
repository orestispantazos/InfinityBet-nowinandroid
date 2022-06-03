package com.infinitybet.premierleague.core.database

import com.infinitybet.premierleague.core.database.dao.AuthorDao
import com.infinitybet.premierleague.core.database.dao.EpisodeDao
import com.infinitybet.premierleague.core.database.dao.NewsResourceDao
import com.infinitybet.premierleague.core.database.dao.PredictionDao
import com.infinitybet.premierleague.core.database.dao.TopicDao
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
