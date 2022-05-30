package com.google.samples.apps.nowinandroid.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.samples.apps.nowinandroid.core.database.dao.AuthorDao
import com.google.samples.apps.nowinandroid.core.database.dao.EpisodeDao
import com.google.samples.apps.nowinandroid.core.database.dao.NewsResourceDao
import com.google.samples.apps.nowinandroid.core.database.dao.PredictionDao
import com.google.samples.apps.nowinandroid.core.database.dao.TopicDao
import com.google.samples.apps.nowinandroid.core.database.model.AuthorEntity
import com.google.samples.apps.nowinandroid.core.database.model.EpisodeAuthorCrossRef
import com.google.samples.apps.nowinandroid.core.database.model.EpisodeEntity
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceAuthorCrossRef
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceEntity
import com.google.samples.apps.nowinandroid.core.database.model.NewsResourceTopicCrossRef
import com.google.samples.apps.nowinandroid.core.database.model.PredictionEntity
import com.google.samples.apps.nowinandroid.core.database.model.TopicEntity
import com.google.samples.apps.nowinandroid.core.database.util.InstantConverter
import com.google.samples.apps.nowinandroid.core.database.util.NewsResourceTypeConverter

@Database(
    entities = [
        AuthorEntity::class,
        EpisodeAuthorCrossRef::class,
        EpisodeEntity::class,
        NewsResourceAuthorCrossRef::class,
        NewsResourceEntity::class,
        NewsResourceTopicCrossRef::class,
        TopicEntity::class,
        PredictionEntity::class,
    ],
    version = 10,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NewsResourceTypeConverter::class,
)
abstract class NiaDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun authorDao(): AuthorDao
    abstract fun predictionDao(): PredictionDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun newsResourceDao(): NewsResourceDao
}
