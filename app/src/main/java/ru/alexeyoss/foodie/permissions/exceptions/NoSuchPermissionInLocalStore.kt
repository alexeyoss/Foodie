package ru.alexeyoss.foodie.permissions.exceptions

import ru.alexeyoss.foodie.core.common.exceptions.AppException

class NoSuchPermissionInLocalStore(
    message: String = "No such permission into the request code local store. Check your permission request",
) : AppException(message)
