package sample.latest.news.features.movie.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListNetwork(
    val movies: List<MovieNetwork> = emptyList()
)
