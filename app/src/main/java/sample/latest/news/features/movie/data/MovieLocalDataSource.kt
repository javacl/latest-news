package sample.latest.news.features.movie.data

import androidx.room.withTransaction
import sample.latest.news.core.db.AppDb
import sample.latest.news.features.movie.data.entity.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(
    private val appDb: AppDb,
    private val movieDao: MovieDao
) {
    fun getMovieList() = movieDao.getMovieList()

    fun getMovie(id: Int) = movieDao.getMovie(id)

    suspend fun insertMovieList(
        page: Int,
        value: List<MovieEntity>
    ) {
        appDb.withTransaction {
            if (page == 1)
                movieDao.deleteMovieList()
            movieDao.insertMovieList(value)
        }
    }
}
