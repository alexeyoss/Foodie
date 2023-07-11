package ru.alexeyoss.core.common.data

import kotlinx.coroutines.runBlocking


/**
 * Represents the current status of async fetch/operation.
 * @see Container.Loading
 * @see Container.Error
 * @see Container.Success
 */


sealed class Container<out T> {

    /**
     * Convert types into [Container] class.
     */
    fun <R> convert(
        mapper: ((T) -> R)? = null
    ): Container<R> {
        return runBlocking {
            val suspendMapper: (suspend (T) -> R)? = if (mapper == null) {
                null
            } else {
                {
                    mapper(it)
                }
            }
            suspendConvert(suspendMapper)
        }
    }

    /**
     * Convert the [Container] type to another type by using a suspend lambda.
     */
    abstract suspend fun <R> suspendConvert(mapper: (suspend (T) -> R)? = null): Container<R>

    /**
     * Get the endorsed container value or throw exception.
     */
    abstract fun extract(): T

    /**
     * Get the endorsed container value or throw exception or return null.
     */
    abstract fun extractOrNull(): T?


    /**
     * The operation is in progress.
     */
    object Loading : Container<Nothing>() {
        override suspend fun <R> suspendConvert(mapper: (suspend (Nothing) -> R)?): Container<R> {
            return this
        }

        override fun extract(): Nothing {
            throw IllegalStateException("Container is Loading")
        }

        override fun extractOrNull(): Nothing? {
            return null
        }

    }

    /**
     * The operation has been finished with success.
     */
    data class Success<T>(
        val value: T
    ) : Container<T>() {

        override suspend fun <R> suspendConvert(mapper: (suspend (T) -> R)?): Container<R> {
            if (mapper == null) throw IllegalStateException("Can't convert Container.Success without mapper")

            return try {
                Success(mapper(value))
            } catch (e: Exception) {
                Error(e)
            }
        }

        override fun extract(): T {
            return value
        }

        override fun extractOrNull(): T? {
            return value
        }

    }

    /**
     * The operation has been failed.
     */
    data class Error(
        val exception: Exception
    ) : Container<Nothing>() {
        override suspend fun <R> suspendConvert(mapper: (suspend (Nothing) -> R)?): Container<R> {
            return this
        }

        override fun extract(): Nothing {
            throw exception
        }

        override fun extractOrNull(): Nothing? {
            return null
        }
    }
}