package com.infinitybet.premierleague.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.infinitybet.premierleague.core.datastore.UserPreferences
import com.infinitybet.premierleague.core.network.Dispatcher
import com.infinitybet.premierleague.core.network.NiaDispatchers.IO
import com.infinitybet.premierleague.core.datastore.IntToStringIdsMigration
import com.infinitybet.premierleague.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
            migrations = listOf(
                IntToStringIdsMigration,
            )
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
