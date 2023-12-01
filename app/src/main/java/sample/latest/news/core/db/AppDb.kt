package sample.latest.news.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import sample.latest.news.features.movie.data.MovieDao
import sample.latest.news.features.movie.data.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
