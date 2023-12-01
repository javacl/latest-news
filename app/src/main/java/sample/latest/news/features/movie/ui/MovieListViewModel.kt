package sample.latest.news.features.movie.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import sample.latest.news.core.util.extensions.PerPageSize
import sample.latest.news.core.util.viewModel.BaseViewModel
import sample.latest.news.features.movie.domain.GetMovieListLocal
import sample.latest.news.features.movie.domain.GetMovieListRemote
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getMovieListLocal: GetMovieListLocal,
    private val getMovieListRemote: GetMovieListRemote
) : BaseViewModel() {

    private var getMovieListJob: Job? = null

    private var currentPage = 1

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val movieList = getMovieListLocal()
        .flowOn(Dispatchers.IO)

    init {
        getData()
    }

    private fun getData(withDelay: Boolean = false) {

        getMovieListJob?.let {
            if (it.isActive)
                it.cancel()
        }

        getMovieListJob = viewModelScope.launch(Dispatchers.IO) {

            if (withDelay)
                delay(500)

            if (currentPage == 1)
                networkLoading(MovieRequestTag.GetMovieList.name)
            else networkMoreLoading(MovieRequestTag.GetMovieList.name)

            observeNetworkState(
                getMovieListRemote(
                    search = _search.value,
                    page = currentPage,
                    perPageSize = PerPageSize,
                    type = MovieType.Foreign.value
                ),
                MovieRequestTag.GetMovieList.name
            )
        }
    }

    fun refresh(withDelay: Boolean = false) {
        currentPage = 1
        getData(withDelay = withDelay)
    }

    fun getNextPage() {
        currentPage++
        getData()
    }

    fun setSearch(value: String) {
        _search.value = value
        refresh(withDelay = true)
    }
}
