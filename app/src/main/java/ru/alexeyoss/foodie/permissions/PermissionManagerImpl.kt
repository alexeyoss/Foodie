package ru.alexeyoss.foodie.permissions

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import ru.alexeyoss.foodie.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.permission.PermissionRequest
import ru.alexeyoss.foodie.permissions.exceptions.NoSuchPermissionInLocalStore
import javax.inject.Inject

/**
 * Class for working with permissions
 * @see PermissionManager
 * */
class PermissionManagerImpl @Inject constructor(
    private val activeActivityHolder: ActiveActivityHolder,
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : PermissionManager {

    override val permissionEventEmitter = MutableSharedFlow<PermissionEvent>()

    private val localRequestStore = HashMap<Int, PermissionRequest>()

    /**
     * Perform permission request
     * */
    override suspend fun request(permissionRequest: PermissionRequest) {
        getActivity()?.requestPermissions(permissionRequest.permissions, permissionRequest.hashCode()).also {
            saveRequestCodeToLocalStore(permissionRequest)
        }
    }

    /**
     * Check permission result. Result will delegate from activity to [PermissionManager] by overriding
     * [AppCompatActivity.onRequestPermissionsResult]
     * */
    override suspend fun onPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionRequest = checkNotNull(
            localRequestStore[requestCode].also { permissionRequest ->
                if (permissionRequest == null) {
                    permissionEventEmitter.emit(
                        PermissionEvent.Error(NoSuchPermissionInLocalStore())
                    )
                }
            }
        )

        val permissionsMap = parseResultToMap(permissions, grantResults)

        if (permissionsMap.all { permission -> permission.value }) {
            permissionEventEmitter.emit(PermissionEvent.Success(PermissionStatus.GRANTED))
            localRequestStore.remove(requestCode)
        } else {
            if (shouldShowRequestPermissionRationale(permissionRequest)) {
                permissionEventEmitter.emit(PermissionEvent.Success(PermissionStatus.DENIED))
            } else {
                permissionEventEmitter.emit(PermissionEvent.Success(PermissionStatus.DENIED_FOREVER))
                localRequestStore.remove(requestCode)
            }
        }
    }

    /**
     * Transform standard permission result to [Map] data structure
     * */
    private suspend fun parseResultToMap(
        permissions: Array<out String>,
        grantResults: IntArray
    ): Map<String, Boolean> = withContext(defaultDispatcher) {
        val grantState = grantResults.map { result ->
            result == PackageManager.PERMISSION_GRANTED
        }
        return@withContext permissions.zip(grantState).toMap()
    }

    /**
     * Save permission request to local store
     * */
    private fun saveRequestCodeToLocalStore(permissionRequest: PermissionRequest) {
        localRequestStore[permissionRequest.hashCode()] = permissionRequest
    }

    /**
     * Check possibility to execute permission request again
     * */
    private suspend fun shouldShowPermissionRationale(permission: String): Boolean {
        return getActivity()?.shouldShowRequestPermissionRationale(permission) ?: false
    }

    /**
     * Trying to get current active activity if NULL emit error into [permissionEventEmitter]
     * */
    private suspend fun getActivity(): FragmentActivity? {
        return try {
            checkNotNull(activeActivityHolder.activity)
        } catch (e: IllegalStateException) {
            permissionEventEmitter.emit(PermissionEvent.Error(e))
            null
        }
    }

    /**
     * Check that all permission are GRANTED
     * */
    override suspend fun checkPermissionsStatus(permissionRequest: PermissionRequest): Boolean {
        val currentActivity = checkNotNull(getActivity())
        return permissionRequest.permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                currentActivity, permission
            ) == PermissionChecker.PERMISSION_GRANTED
        }
    }

    /**
     * Check possibility to execute permission request again
     * */
    private suspend fun shouldShowRequestPermissionRationale(permissionRequest: PermissionRequest): Boolean =
        permissionRequest
            .permissions
            .any { permission -> shouldShowPermissionRationale(permission) }
}
