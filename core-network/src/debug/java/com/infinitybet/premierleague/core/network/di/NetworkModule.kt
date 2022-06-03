package com.infinitybet.premierleague.core.network.di

import com.infinitybet.premierleague.core.network.NiaNetwork
import com.infinitybet.premierleague.core.network.fake.FakeNiaNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsNiaNetwork(
        niANetwork: FakeNiaNetwork
    ): NiaNetwork

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}
