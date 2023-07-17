package ru.alexeyoss.services.permission

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.*
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.alexeyoss.core.common.activity.ActiveActivityHolder
import ru.alexeyoss.core.common.di.CoroutinesModule
import ru.alexeyoss.services.preference.prefs.PermissionsPrefs
import timber.log.Timber
import javax.inject.Inject

// TODO REFACTOR
/**
 * Класс для проверки и запросов разрешений.
 */
class PermissionManager
@Inject constructor(
    @CoroutinesModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val activeActivityHolder: ActiveActivityHolder,
    private val permissionsPrefs: PermissionsPrefs
) {
    private val mainJob = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.i(throwable) }
    private val uiScope = CoroutineScope(mainDispatcher + mainJob + exceptionHandler)
    fun activityResultCallback(result: Map<String, Boolean>) {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && result.keys.toTypedArray()
                .contentEquals(locationPermissions)
        ) {
            result.values.any { it }
        } else {
            result.values.all { it }
        }
    }

    private fun performPermissionRequest(permissionRequest: PermissionRequest): Flow<Boolean> {
//        registerActivityResultContract.registerActivityResult(
//            RequestMultiplePermissions(),
//            permissionRequest.permissions
//        )
        return flowOf(false)
    }

    /**
     * Проверить наличие всех разрешений в [PermissionRequest].
     *
     * @param permissionRequest Проверяемый [PermissionRequest].
     *
     * @return Статус разрешения [PermissionStatus].
     */
    suspend fun check(permissionRequest: PermissionRequest): PermissionStatus {
        return checkInternal(permissionRequest).first()
    }

    /**
     * Запросить разрешение.
     *
     * @param permissionRequest Выполняемый [PermissionRequest].
     *
     * @return [Flow], содержащий [Boolean]: true, если разрешение выдано, false - если нет.
     */

    suspend fun request(permissionRequest: PermissionRequest): Flow<Boolean> {
        return checkInternal(permissionRequest).flatMapLatest { permissionRequestStatus ->
            if (permissionRequestStatus.isGranted) {
                flowOf(true)
            } else {
                showPermissionRequestRationalIfNeeded(permissionRequest).flatMapLatest {
                    performPermissionRequestByDialogOrSettings(
                        permissionRequest, permissionRequestStatus
                    )
                }.onEach { isGranted ->
                    setPermissionRequestIsRequested(permissionRequest)
                    setPermissionRequestIsGranted(permissionRequest, isGranted)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun checkInternal(permissionRequest: PermissionRequest): Flow<PermissionStatus> {
        return isPermissionRequestGranted(permissionRequest).flatMapLatest { isGranted ->
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

    // TODO check is it useful?
    private fun cancelAllJobs() {
        uiScope.coroutineContext.cancelChildren()
    }


    private fun isPermissionRequestGranted(permissionRequest: PermissionRequest): Flow<Boolean> {
        val requestPermissions = permissionRequest.permissions
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val checkedPermissions = requestPermissions.map { permission -> isPermissionGranted(permission) }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && requestPermissions.contentEquals(
                locationPermissions
            )
        ) {
            flowOf(checkedPermissions.any { it })
        } else {
            flowOf(checkedPermissions.all { it })
        }
    }


    private fun showPermissionRequestRationalIfNeeded(permissionRequest: PermissionRequest): Flow<Boolean> {
        return if (needToShowPermissionRequestRational(permissionRequest)) flowOf(true) else flowOf(false)
//            showPermissionRequestRational(permissionRequest)
    }

    private fun needToShowPermissionRequestRational(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.showPermissionsRational && shouldShowRequestPermissionRationale(permissionRequest)

    private fun performPermissionRequestByDialogOrSettings(
        permissionRequest: PermissionRequest, permissionStatus: PermissionStatus
    ): Flow<Boolean> {
        return when {
            permissionStatus != PermissionStatus.DENIED_FOREVER -> performPermissionRequest(permissionRequest)
            permissionRequest.showSettingsRational -> flowOf(false) // TODO performPermissionRequestBySettings(permissionRequest)
            else -> flowOf(false)
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

    private suspend fun isPermissionRequested(permission: String) = withContext(ioDispatcher) {
        permissionsPrefs.isPermissionRequested(permission)
    }

    private fun isPermissionGranted(permission: String): Boolean = safeGetActivity().let { fragmentActivity ->
        ContextCompat.checkSelfPermission(
            fragmentActivity, permission
        ) == PERMISSION_GRANTED
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

    private fun safeGetActivity(): FragmentActivity {
        if (activeActivityHolder.isActivityExist) return activeActivityHolder.activity!!
        else throw NullPointerException()
    }

    private companion object {
        const val GRANTED_PREFIX = "granted"
    }


}
