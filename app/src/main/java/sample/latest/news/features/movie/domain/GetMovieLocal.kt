package sample.latest.news.features.movie.domain

import sample.latest.news.features.movie.data.MovieRepository
import javax.inject.Inject

class GetMovieLocal @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(id: Int) = movieRepository.getMovieLocal(id)
}
