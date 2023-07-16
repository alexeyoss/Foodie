package ru.alexeyoss.services.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.alexeyoss.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.core.common.di.CoroutinesModule
import ru.alexeyoss.services.preference.prefs.PermissionsPrefs
import timber.log.Timber
import javax.inject.Inject

/**
 * Класс для проверки и запросов разрешений.
 */
class PermissionManager
@Inject constructor(
    @CoroutinesModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val activeActivityHolder: ActiveActivityHolder,
    private val permissionsPrefs: PermissionsPrefs,
) {

    private val mainJob = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.i(throwable) }
    private val uiScope = CoroutineScope(mainDispatcher + mainJob + exceptionHandler)

    private suspend fun performPermissionRequest(permissionRequest: PermissionRequest): Flow<Boolean> {
        return callbackFlow {
            safeGetActivity().registerForActivityResult(
                RequestMultiplePermissions()
            ) { grantResult ->
                // TODO implement correct workflow
                if (grantResult.all { perm -> perm.value }) trySend(true) else trySend(false)
            }.launch(permissionRequest.permissions)
        }
    }

    /**
     * Проверить наличие всех разрешений в [PermissionRequest].
     *
     * @param permissionRequest Проверяемый [PermissionRequest].
     *
     * @return Статус разрешения [PermissionStatus].
     */
    suspend fun check(permissionRequest: PermissionRequest): PermissionStatus {
        return checkInternal(permissionRequest).single()
    }

    /**1
     * Запросить разрешение.
     *
     * @param permissionRequest Выполняемый [PermissionRequest].
     *
     * @return [Flow], содержащий [Boolean]: true, если разрешение выдано, false - если нет.
     */

    suspend fun request(permissionRequest: PermissionRequest): Flow<Boolean> {
//        cancelAllJobs()
        return checkInternal(permissionRequest).flatMapMerge { permissionRequestStatus ->
            if (permissionRequestStatus.isGranted) {
                flowOf(true)
            } else {
                showPermissionRequestRationalIfNeeded(permissionRequest)
                    .flatMapMerge {
                        performPermissionRequestByDialogOrSettings(
                            permissionRequest, permissionRequestStatus
                        )
                    }.onEach { isGranted ->
                        // TODO not working
                        setPermissionRequestIsRequested(permissionRequest)
                        setPermissionRequestIsGranted(permissionRequest, isGranted)
                    }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun checkInternal(permissionRequest: PermissionRequest): Flow<PermissionStatus> {
        return isPermissionRequestGranted(permissionRequest).flatMapMerge { isGranted ->
            val status = when {
                isGranted -> PermissionStatus.GRANTED
                !isPermissionRequestRequested(permissionRequest) -> PermissionStatus.NOT_REQUESTED
                isPermissionRequestLastGranted(permissionRequest) -> PermissionStatus.GRANTED_ONCE
                isPermissionRequestDenied(permissionRequest) -> PermissionStatus.DENIED
                else -> PermissionStatus.DENIED_FOREVER
            }
            flowOf(status)
        }
    }

    private suspend fun onPermissionResult(results: Map<String, Boolean>) {
        if (results.all { permission -> permission.value }) {
            // TODO set permission location to toolbar
        } else {

        }
    }
//
//    private suspend fun subscribeToSettingsResult() {
//        val route = DefaultSettingsRationalRoute("")
//        settingsJob = coroutineScope {
//            launch {
//                screenResultObserver.observeScreenResult(route).firstElement().subscribe()
//            }
//        }
//    }
//
//    private fun subscribeToRationalResult() {
//        val route = DefaultPermissionRationalRoute("")
//        rationalDisposable = screenResultObserver.observeScreenResult(route).firstElement().subscribe()
//    }
//
//    private fun subscribeToPermissionResult() {
//        val route = PermissionRequestRoute(emptyArray())
//        permissionDisposable = screenResultObserver.observeScreenResult(route).firstOrError().subscribe({}, {})
//    }

    private fun cancelAllJobs() {
        uiScope.coroutineContext.cancelChildren()
    }


    private suspend fun isPermissionRequestGranted(permissionRequest: PermissionRequest): Flow<Boolean> {
        val requestPermissions = permissionRequest.permissions
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val checkedPermissions = requestPermissions.map { permission -> isPermissionGranted(permission) }
        return flow {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && requestPermissions.contentEquals(locationPermissions)) {
                checkedPermissions.any { it }
            } else {
                checkedPermissions.all { it }
            }
        }
    }


    private fun showPermissionRequestRationalIfNeeded(permissionRequest: PermissionRequest): Flow<Boolean> {
        return callbackFlow {
            if (needToShowPermissionRequestRational(permissionRequest)) trySend(true) else trySend(false)
//            showPermissionRequestRational(permissionRequest)
        }
    }

    private fun needToShowPermissionRequestRational(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.showPermissionsRational && shouldShowRequestPermissionRationale(permissionRequest)

    private suspend fun performPermissionRequestByDialogOrSettings(
        permissionRequest: PermissionRequest,
        permissionStatus: PermissionStatus
    ): Flow<Boolean> = callbackFlow {
        when {
            permissionStatus != PermissionStatus.DENIED_FOREVER -> performPermissionRequest(permissionRequest)
            permissionRequest.showSettingsRational -> Unit// TODO performPermissionRequestBySettings(permissionRequest)
            else -> trySend(false)
        }
    }

    /**
     * Сохраняет в хранилище [Boolean] параметр, информирующий о том, производился ли запрос разрешения у пользователя
     * @see setPermissionIsRequested
     * */
    private suspend fun setPermissionRequestIsRequested(permissionRequest: PermissionRequest) =
        permissionRequest.permissions.forEach { permission -> setPermissionIsRequested(permission) }

    private suspend fun setPermissionIsRequested(permission: String) =
        permissionsPrefs.setPermissionIsRequested(permission, true)

    /**
     * Сохраняет в хранилище [Boolean] параметры о предоставленных пользовательских разрешения
     * @see setPermissionIsGranted
     * */
    private suspend fun setPermissionRequestIsGranted(
        permissionRequest: PermissionRequest, isGranted: Boolean
    ) = permissionRequest.permissions.forEach { permission -> setPermissionIsGranted(permission, isGranted) }

    private suspend fun setPermissionIsGranted(permission: String, isGranted: Boolean) =
        permissionsPrefs.setPermissionIsGranted(GRANTED_PREFIX + permission, isGranted)


    private fun shouldShowRequestPermissionRationale(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.any { permission ->
            runBlocking {
                shouldShowPermissionRationale(permission)
            }
        }

    private fun isPermissionRequestDenied(permissionRequest: PermissionRequest): Boolean =
        shouldShowRequestPermissionRationale(permissionRequest)

    private suspend fun isPermissionRequestRequested(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.all { permission -> isPermissionRequested(permission) }

    private suspend fun isPermissionGranted(permission: String): Boolean = with(mainDispatcher) {
        // TODO not working
        return false
        // safeGetActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    }


    private suspend fun isPermissionRequested(permission: String) = withContext(ioDispatcher) {
        permissionsPrefs.isPermissionRequested(permission)
    }

    private suspend fun isPermissionRequestLastGranted(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.all { permission -> isPermissionLastGranted(permission) }

    private suspend fun isPermissionLastGranted(permission: String) = withContext(ioDispatcher) {
        permissionsPrefs.isPermissionLastGranted(GRANTED_PREFIX + permission)
    }


//    private fun showPermissionRequestRational(permissionRequest: PermissionRequest): Flow<Boolean> {
//        return suspendCancellableCoroutine { continuation ->
//            val customPermissionsRationalRoute = permissionRequest.permissionsRationalRoute
//            val customPermissionsRationalStr = permissionRequest.permissionsRationalStr
//
//            val permissionRationalRoute = when {
//                customPermissionsRationalRoute != null -> {
//                    startAndObserveReturnFromScreenCustom(customPermissionsRationalRoute)
//                }
//
//                customPermissionsRationalStr != null -> {
//                    Timber.i("Separate navigation into independent module")
////                Screen.permissionExplanationDialog(customPermissionsRationalStr)
//                }
//
//                else -> !continuation.cancel(PermissionsRationalIsNotProvidedException())
//            }
//        }
//
//        return startAndObserveReturnFromScreen(permissionRationalRoute)
//    }
//
//    private fun performPermissionRequestBySettings(permissionRequest: PermissionRequest): Single<Boolean> {
//        val customSettingsRationalRoute = permissionRequest.settingsRationalRoute
//        val customSettingsRationalStr = permissionRequest.settingsRationalStr
//
//        val settingsRationalRoute = when {
//            customSettingsRationalRoute != null -> {
//                return startAndObserveReturnFromScreenCustom(customSettingsRationalRoute).andThen(checkInternal(
//                    permissionRequest
//                ).map { it.isGranted })
//            }
//
//            customSettingsRationalStr != null -> DefaultSettingsRationalRoute(
//                customSettingsRationalStr
//            )
//
//            else -> return Single.error(SettingsRationalIsNotProvidedException())
//        }
//        return startAndObserveReturnFromScreen(settingsRationalRoute).andThen(checkInternal(permissionRequest).map { it.isGranted })
//    }


    /**
     * Проверить, следует ли показывать пользователю объяснение, для чего нужен запрашиваемый Permission.
     *
     * @param permission Проверяемое разрешение.
     *
     * @return True, если во время предыдущего запроса разрешения пользователь нажал отказ и не выбрал опцию "Don't ask
     * again", false - если разрешение запрашивается в первый раз, или если во время предыдущего запроса разрешения
     * пользователь выбрал опцию "Don't ask again".
     */
    private suspend fun shouldShowPermissionRationale(permission: String): Boolean = withContext(mainDispatcher) {
        safeGetActivity().let { activity ->
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }
    }

//    private fun startAndObserveReturnFromScreenCustom(route: Screen): PermissionState {
//        return screenResultObserver.observeScreenResult(route as ResultRoute<Serializable>).firstElement()
//            .flatMapCompletable { Completable.complete() }.doOnSubscribe { commandExecutor.execute(Start(route)) }
//    }
//
//    private fun <T : java.io.Serializable> startAndObserveReturnFromScreen(route: ActivityWithResultRoute<T>): Completable {
//        return screenResultObserver.observeScreenResult(route).firstElement()
//            .flatMapCompletable { Completable.complete() }
//            .doOnSubscribe { commandExecutor.execute(StartForResult(route)) }
//    }

    private suspend fun safeGetActivity(): FragmentActivity = withContext(mainDispatcher) {
        activeActivityHolder.activityFlow.first()
    }

    private companion object {
        const val GRANTED_PREFIX = "granted"
    }


}
