package sample.latest.news.features.movie.data

import sample.latest.news.core.api.BaseRemoteDataSource
import sample.latest.news.core.api.safeApiCall
import sample.latest.news.core.util.extensions.PerPageSize
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseRemoteDataSource() {

    suspend fun getMovieList(
        search: String,
        page: Int,
        perPageSize: Int,
        type: String
    ) = safeApiCall(
        call = {
            requestGetMovieList(
                search = search,
                page = page,
                perPageSize = perPageSize,
                type = type
            )
        },
        errorMessage = "Error get movie list"
    )

    private suspend fun requestGetMovieList(
        search: String,
        page: Int,
        perPageSize: Int,
        type: String
    ) = checkApiResult(
        movieService.getMovieList(
            search = search,
            page = page,
            perPageSize = perPageSize,
            type = type
        )
    )
}
