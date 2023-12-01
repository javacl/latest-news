package sample.latest.news.features.movie.ui

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import sample.latest.news.core.util.navigation.NavigationKey
import sample.latest.news.core.util.viewModel.BaseViewModel
import sample.latest.news.features.movie.domain.GetMovieListLocal
import sample.latest.news.features.movie.domain.GetMovieLocal
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieListLocal: GetMovieListLocal,
    getMovieLocal: GetMovieLocal
) : BaseViewModel() {

    private val id by lazy {
        savedStateHandle.get<String>(NavigationKey.KEY_ID)?.toIntOrNull() ?: 0
    }

    val movie = getMovieLocal(id)
        .flowOn(Dispatchers.IO)

    val movieSimilarList = getMovieListLocal().map {
        it?.filter { item -> item.id != id }?.shuffled()
    }.flowOn(Dispatchers.IO)

    private val _showFullInfo = MutableStateFlow(false)
    val showFullInfo = _showFullInfo.asStateFlow()

    fun toggleShowFullInfo() {
        _showFullInfo.value = !_showFullInfo.value
    }
}
