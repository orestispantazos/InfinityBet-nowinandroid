package com.infinitybet.premierleague.core.data.test

import com.infinitybet.premierleague.core.data.di.DataModule
import com.infinitybet.premierleague.core.data.repository.AuthorsRepository
import com.infinitybet.premierleague.core.data.repository.NewsRepository
import com.infinitybet.premierleague.core.data.repository.PredictionsRepository
import com.infinitybet.premierleague.core.data.repository.TopicsRepository
import com.infinitybet.premierleague.core.data.repository.fake.FakeAuthorsRepository
import com.infinitybet.premierleague.core.data.repository.fake.FakeNewsRepository
import com.infinitybet.premierleague.core.data.repository.fake.FakePredictionsRepository
import com.infinitybet.premierleague.core.data.repository.fake.FakeTopicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {
    @Binds
    fun bindsTopicRepository(
        fakeTopicsRepository: FakeTopicsRepository
    ): TopicsRepository

    @Binds
    fun bindsAuthorRepository(
        fakeAuthorsRepository: FakeAuthorsRepository
    ): AuthorsRepository

    @Binds
    fun bindsPredictionRepository(
        fakePredictionsRepository: FakePredictionsRepository
    ): PredictionsRepository

    @Binds
    fun bindsNewsResourceRepository(
        fakeNewsRepository: FakeNewsRepository
    ): NewsRepository
}
