package sample.latest.news.features.movie.domain

import sample.latest.news.features.movie.data.MovieRepository
import javax.inject.Inject

class GetMovieListRemote @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        search: String,
        page: Int,
        perPageSize: Int,
        type: String
    ) = movieRepository.getMovieListRemote(
        search = search,
        page = page,
        perPageSize = perPageSize,
        type = type
    )
}
