package sample.latest.news.features.movie.data.entity

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
@Entity(primaryKeys = ["id"])
data class MovieEntity(
    val id: Int = 0,
    val name: String = "",
    val engName: String = "",
    val story: String = "",
    val generalDownloadPage: String = "",
    val poster: String = ""
)
