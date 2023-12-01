package sample.latest.news.features.movie.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sample.latest.news.features.movie.data.network.MovieListNetwork

interface MovieService {

    @GET("/share/v1/colleagues/downloadable_movies")
    suspend fun getMovieList(
        @Query("by_name") search: String,
        @Query("page") page: Int,
        @Query("per_page") perPageSize: Int,
        @Query("by_movies[movie_type]") type: String
    ): Response<MovieListNetwork>
}
