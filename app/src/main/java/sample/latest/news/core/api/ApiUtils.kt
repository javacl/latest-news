package sample.latest.news.core.api

import android.util.Log
import org.json.JSONObject
import sample.latest.news.core.model.AppError

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [ApiResult.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> ApiResult<T>,
    errorMessage: String
): ApiResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Log.d("IOException", e.message.toString())
        ApiResult.Error(Exceptions.IOException(errorMessage, e))
    }
}

fun errorParser(errorBody: String?): AppError {

    return if (errorBody != null) {

        val errorBodyObject = JSONObject(errorBody)

        val message = errorBodyObject.getString("message").toString()

        AppError(message = message)

    } else {
        AppError()
    }
}
