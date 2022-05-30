package com.google.samples.apps.nowinandroid.core.data.di

import com.google.samples.apps.nowinandroid.core.data.repository.AuthorsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.NewsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.OfflineFirstAuthorsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.OfflineFirstNewsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.OfflineFirstPredictionsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.OfflineFirstTopicsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.PredictionsRepository
import com.google.samples.apps.nowinandroid.core.data.repository.TopicsRepository
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
