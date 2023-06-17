package ru.alexeyoss.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import timber.log.Timber

abstract class BaseUseCase(
    private val dispatcher: CoroutineDispatcher
) {


    val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
        createScope()
    }

    val scope = createScope()
    private fun createScope(): CoroutineScope = CoroutineScope(Job() + dispatcher)


}