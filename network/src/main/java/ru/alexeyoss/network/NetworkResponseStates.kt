package ru.alexeyoss.network

import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Network response states
 * */
sealed interface ResponseStates<out T> {
    data class Success<T>(val data: T) : ResponseStates<T>
    data class Error(val throwable: Throwable) : ErrorState
}

/**
 * Network Errors states
 * @see NetworkErrorResponse
 * */
sealed interface ErrorState : ResponseStates<Nothing> {
    data class GenericError(val throwable: Throwable) : ErrorState
    data class ServerError(
        val code: Int? = null, val message: String? = null, val response: NetworkErrorResponse? = null
    ) : ErrorState

    object ConnectionError : ErrorState
}

data class NetworkErrorResponse(
    val code: String,
    val reason: String
)

internal fun checkThrowable(throwable: Throwable): ResponseStates<Nothing> = when (throwable) {
    is HttpException -> parseHttpException(throwable)
    is UnknownHostException -> ErrorState.ConnectionError
    else -> ErrorState.GenericError(throwable)
}

internal fun parseHttpException(exception: HttpException): ResponseStates<Nothing> {
    return runCatching {
        val response = exception.response()?.let {
            GsonBuilder().create().fromJson(it.body().toString(), NetworkErrorResponse::class.java)
        }
        if (response != null) {
            ErrorState.ServerError(
                exception.code(), exception.message(), response
            )
        } else {
            ErrorState.ServerError()
        }
    }.getOrElse {
        ErrorState.GenericError(it)
    }
}