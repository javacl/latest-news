package sample.latest.news.features.movie.data

import sample.latest.news.core.api.ApiResult
import sample.latest.news.core.api.Exceptions
import sample.latest.news.core.util.NetworkHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val networkHandler: NetworkHandler
) {
    fun getMovieListLocal() = movieLocalDataSource.getMovieList()

    fun getMovieLocal(id: Int) = movieLocalDataSource.getMovie(id)

    suspend fun getMovieListRemote(
        search: String,
        page: Int,
        perPageSize: Int,
        type: String
    ): ApiResult<Unit> {
        return if (networkHandler.hasNetworkConnection()) {
            when (val result = movieRemoteDataSource.getMovieList(
                search = search,
                page = page,
                perPageSize = perPageSize,
                type = type
            )) {
                is ApiResult.Success -> ApiResult.Success(
                    movieLocalDataSource.insertMovieList(
                        page = page,
                        value = result.data.movies.map { it.toMovieEntity() }
                    )
                )

                is ApiResult.Error -> ApiResult.Error(result.exception)
            }
        } else ApiResult.Error(Exceptions.NetworkConnectionException())
    }
}
