package ru.alexeyoss.core.common.exceptions


/**
 * Any in-app managed exceptions.
 */
open class AppException(
    message: String = "",
    cause: Throwable? = null,
) : Exception(message, cause)


/**
 * Problems with internet connection
 */
class ConnectionException(cause: Exception? = null) : AppException(cause = cause)

/**
 * Common exception
 */
class CommonException(
    message: String = "",
    cause: Exception? = null
) : AppException(message = message, cause = cause)