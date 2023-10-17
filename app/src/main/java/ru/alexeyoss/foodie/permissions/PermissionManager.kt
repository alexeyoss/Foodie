package ru.alexeyoss.foodie.permissions

import kotlinx.coroutines.flow.MutableSharedFlow
import ru.alexeyoss.foodie.core.common.permission.PermissionRequest

interface PermissionManager {
    val permissionEventEmitter: MutableSharedFlow<PermissionEvent>
    suspend fun request(permissionRequest: PermissionRequest)
    suspend fun checkPermissionsStatus(permissionRequest: PermissionRequest): Boolean
    suspend fun onPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}
