package ru.alexeyoss.foodie.permissions

sealed interface PermissionEvent {
    data class Success(val permissionStatus: PermissionStatus) : PermissionEvent
    data class Error(val exp: Exception) : PermissionEvent
}
