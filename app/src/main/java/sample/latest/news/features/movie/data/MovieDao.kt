package sample.latest.news.features.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import sample.latest.news.features.movie.data.entity.MovieEntity

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM MovieEntity")
    fun getMovieList(): Flow<List<MovieEntity>?>

    @Transaction
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    fun getMovie(id: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(value: List<MovieEntity>)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteMovieList()
}
