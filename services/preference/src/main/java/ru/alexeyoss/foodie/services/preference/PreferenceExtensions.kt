package ru.alexeyoss.foodie.services.preference

import timber.log.Timber

internal suspend inline fun saveOperation(
    prefDataStoreName: String,
    noinline block: suspend () -> Unit
): Boolean {
    return try {
        block()
        true
    } catch (e: Exception) {
        Timber.e(e, prefDataStoreName)
        false
    }
}