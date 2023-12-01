package sample.latest.news.features.movie.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sample.latest.news.features.movie.data.entity.MovieEntity

@JsonClass(generateAdapter = true)
data class MovieNetwork(
    val id: Int = 0,
    val name: String = "",
    @Json(name = "eng_name")
    val engName: String = "",
    val story: String = "",
    @Json(name = "general_download_page")
    val generalDownloadPage: String = "",
    val poster: String = ""
) {
    fun toMovieEntity() = MovieEntity(
        id = id,
        name = name,
        engName = engName,
        story = story,
        generalDownloadPage = generalDownloadPage,
        poster = poster
    )
}
