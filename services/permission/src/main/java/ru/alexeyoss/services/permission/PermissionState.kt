package ru.alexeyoss.services.permission

sealed interface PermissionState {
    data class Success<T>(val data: T) : PermissionState
    data class Error(val throwable: Throwable) : PermissionState
}