package ru.alexeyoss.foodie.permissions

enum class PermissionStatus {
    /**
     * Разрешение выдано.
     */
    GRANTED,

    /**
     * При предыдущем запросе в разрешении было отказано.
     */
    DENIED,

    /**
     * При предыдущем запросе в разрешении было отказано и выбрана опция "Don't ask again".
     */
    DENIED_FOREVER,
}
