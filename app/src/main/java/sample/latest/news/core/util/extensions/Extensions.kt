package sample.latest.news.core.util.extensions

import android.content.Context
import sample.latest.news.core.model.NetworkViewState

fun Context.parseServerErrorMessage(networkViewState: NetworkViewState): String {
    return networkViewState.serverErrorMessage ?: getString(networkViewState.errorMessage)
}
