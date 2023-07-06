package ru.alexeyoss.network.utils

import timber.log.Timber

suspend fun <T> safeApiCall(
    block: suspend () -> T
): ResponseStates<T> = runCatching {
    ResponseStates.Success(block())
}.getOrElse {
    Timber.e(it)
    checkThrowable(it)
}

