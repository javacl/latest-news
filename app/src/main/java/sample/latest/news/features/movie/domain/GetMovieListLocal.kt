package sample.latest.news.features.movie.domain

import sample.latest.news.features.movie.data.MovieRepository
import javax.inject.Inject

class GetMovieListLocal @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getMovieListLocal()
}
