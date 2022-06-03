package com.infinitybet.premierleague.core.data.di

import com.infinitybet.premierleague.core.data.repository.AuthorsRepository
import com.infinitybet.premierleague.core.data.repository.NewsRepository
import com.infinitybet.premierleague.core.data.repository.OfflineFirstAuthorsRepository
import com.infinitybet.premierleague.core.data.repository.OfflineFirstNewsRepository
import com.infinitybet.premierleague.core.data.repository.OfflineFirstPredictionsRepository
import com.infinitybet.premierleague.core.data.repository.OfflineFirstTopicsRepository
import com.infinitybet.premierleague.core.data.repository.PredictionsRepository
import com.infinitybet.premierleague.core.data.repository.TopicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTopicRepository(
        topicsRepository: OfflineFirstTopicsRepository
    ): TopicsRepository

    @Binds
    fun bindsAuthorsRepository(
        authorsRepository: OfflineFirstAuthorsRepository
    ): AuthorsRepository

    @Binds
    fun bindsPredictionsRepository(
        predictionsRepository: OfflineFirstPredictionsRepository
    ): PredictionsRepository

    @Binds
    fun bindsNewsResourceRepository(
        newsRepository: OfflineFirstNewsRepository
    ): NewsRepository
}
