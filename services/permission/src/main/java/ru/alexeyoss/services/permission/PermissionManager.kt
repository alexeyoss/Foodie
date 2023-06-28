package ru.alexeyoss.services.permission

import android.Manifest
import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.terrakok.cicerone.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import ru.alexeyoss.core.common.ActiveActivityHolder
import ru.alexeyoss.core.common.CoroutinesModule
import ru.alexeyoss.services.permission.exceptions.PermissionsRationalIsNotProvidedException
import timber.log.Timber

/**
 * Класс для проверки и запросов разрешений.
 */
@ExperimentalCoroutinesApi
open class PermissionManager(
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val activeActivityHolder: ActiveActivityHolder,
//    private val commandExecutor: AppCommandExecutorWithResult,
    private val sharedPreferences: SharedPreferences,
//    private val screenResultObserver: ScreenResultObserver
) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.i(throwable) }

    private val settingsJob = Job()
    private val rationalJob = Job()
    private val permissionJob = Job()
//
//    init {
//        subscribeToSettingsResult()
//        subscribeToRationalResult()
//        subscribeToPermissionResult()
//    }

//    protected fun performPermissionRequest(permissionRequest: PermissionRequest): Single<Boolean> {
//        val route = PermissionRequestRoute(permissionRequest.permissions)
//        return screenResultObserver.observeScreenResult(route).firstOrError()
//            .doOnSubscribe { commandExecutor.execute(RequestPermission(route)) }
//    }
//
//    /**
//     * Проверить наличие всех разрешений в [PermissionRequest].
//     *
//     * @param permissionRequest Проверяемый [PermissionRequest].
//     *
//     * @return Статус разрешения [PermissionStatus].
//     */
//    fun check(permissionRequest: PermissionRequest): PermissionStatus {
//        return checkInternal(permissionRequest).blockingGet()
//    }

    /**
     * Запросить разрешение.
     *
     * @param permissionRequest Выполняемый [PermissionRequest].
     *
     * @return [Flow], содержащий [Boolean]: true, если разрешение выдано, false - если нет.
     */

    suspend fun request(permissionRequest: PermissionRequest): Flow<Boolean> {
        cancelAll()
        return checkInternal(permissionRequest).flatMapMerge { permissionRequestStatus ->
            if (permissionRequestStatus.isGranted) {
                flowOf(true)
            } else {
                showPermissionRequestRationalIfNeeded(permissionRequest).toSingleDefault(false).flatMap {
                    performPermissionRequestByDialogOrSettings(
                        permissionRequest, permissionRequestStatus
                    )
                }.doOnSuccess {
                    setPermissionRequestIsRequested(permissionRequest)
                    setPermissionRequestIsGranted(permissionRequest, it)
                }
            }
        }
    }

    private suspend fun checkInternal(permissionRequest: PermissionRequest): Flow<PermissionStatus> {
        return isPermissionRequestGranted(permissionRequest).flatMapMerge { isGranted ->
            flow {
                when {
                    isGranted -> PermissionStatus.GRANTED
                    !isPermissionRequestRequested(permissionRequest) -> PermissionStatus.NOT_REQUESTED
                    isPermissionRequestLastGranted(permissionRequest) -> PermissionStatus.GRANTED_ONCE
                    isPermissionRequestDenied(permissionRequest) -> PermissionStatus.DENIED
                    else -> PermissionStatus.DENIED_FOREVER
                }
            }
        }
    }

    //
//    private fun subscribeToSettingsResult() {
//        val route = DefaultSettingsRationalRoute("")
//        settingsDisposable = screenResultObserver.observeScreenResult(route).firstElement().subscribe()
//    }

    private fun subscribeToRationalResult() {
        val route = DefaultPermissionRationalRoute("")
        rationalDisposable = screenResultObserver.observeScreenResult(route).firstElement().subscribe()
    }

    //    private fun subscribeToPermissionResult() {
//        val route = PermissionRequestRoute(emptyArray())
//        permissionDisposable = screenResultObserver.observeScreenResult(route).firstOrError().subscribe({}, {})
//    }
//
    private fun cancelAll() {
        settingsJob.cancel()
        permissionJob.cancel()
        rationalJob.cancel()
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


    private fun isPermissionRequestDenied(permissionRequest: PermissionRequest): Boolean =
        shouldShowRequestPermissionRationale(permissionRequest)

    private fun isPermissionRequestRequested(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.all { permission -> isPermissionRequested(permission) }

    private fun showPermissionRequestRationalIfNeeded(permissionRequest: PermissionRequest): Flow<Boolean> {
        return if (needToShowPermissionRequestRational(permissionRequest)) {
            showPermissionRequestRational(permissionRequest)
        } else {
            flowOf(false)
        }

    }

//    private fun performPermissionRequestByDialogOrSettings(
//        permissionRequest: PermissionRequest, permissionStatus: PermissionStatus
//    ): Single<Boolean> = when {
//        permissionStatus != PermissionStatus.DENIED_FOREVER -> performPermissionRequest(permissionRequest)
//
//        permissionRequest.showSettingsRational -> performPermissionRequestBySettings(
//            permissionRequest
//        )
//
//        else -> Single.just(false)
//    }
//
//    private fun setPermissionRequestIsRequested(permissionRequest: PermissionRequest) =
//        permissionRequest.permissions.forEach { permission -> setPermissionIsRequested(permission) }
//
//    private fun setPermissionRequestIsGranted(
//        permissionRequest: PermissionRequest, isGranted: Boolean
//    ) = permissionRequest.permissions.forEach { permission -> setPermissionIsGranted(permission, isGranted) }
//

    private suspend fun isPermissionGranted(permission: String): Boolean {
        return safeGetActivity().let { activity ->
            ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_GRANTED
        }
    }

    private fun shouldShowRequestPermissionRationale(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.any { permission ->
            runBlocking {
                shouldShowPermissionRationale(permission)
            }
        }

    private fun isPermissionRequested(permission: String) = sharedPreferences.getBoolean(permission, false)

    private fun isPermissionRequestLastGranted(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.permissions.all { permission -> isPermissionLastGranted(permission) }

    private fun isPermissionLastGranted(permission: String) =
        sharedPreferences.getBoolean(GRANTED_PREFIX + permission, false)

//    private fun setPermissionIsGranted(permission: String, isGranted: Boolean) =
//        sharedPreferences.edit().putBoolean(GRANTED_PREFIX + permission, isGranted).apply()

    private fun needToShowPermissionRequestRational(permissionRequest: PermissionRequest): Boolean =
        permissionRequest.showPermissionsRational && shouldShowRequestPermissionRationale(permissionRequest)

    private fun showPermissionRequestRational(permissionRequest: PermissionRequest): Flow<Boolean> {
        val customPermissionsRationalRoute = permissionRequest.permissionsRationalRoute
        val customPermissionsRationalStr = permissionRequest.permissionsRationalStr

        val permissionRationalRoute = when {
            customPermissionsRationalRoute != null -> {
                return startAndObserveReturnFromScreenCustom(customPermissionsRationalRoute)
            }

            customPermissionsRationalStr != null -> {
                Timber.i("Separate navigation into independent module")
//                Screen.permissionExplanationDialog(customPermissionsRationalStr)
            }

            else -> return Completable.error(PermissionsRationalIsNotProvidedException())
        }

        return startAndObserveReturnFromScreen(permissionRationalRoute)
    }

//    private fun performPermissionRequestBySettings(permissionRequest: PermissionRequest): Single<Boolean> {
//        val customSettingsRationalRoute = permissionRequest.settingsRationalRoute
//        val customSettingsRationalStr = permissionRequest.settingsRationalStr
//
//        val settingsRationalRoute = when {
//            customSettingsRationalRoute != null -> {
//                return startAndObserveReturnFromScreenCustom(customSettingsRationalRoute).andThen(
//                        checkInternal(
//                            permissionRequest
//                        ).map { it.isGranted })
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
//
//    private fun setPermissionIsRequested(permission: String) =
//        sharedPreferences.edit().putBoolean(permission, true).apply()

    /**
     * Проверить, следует ли показывать пользователю объяснение, для чего нужен запрашиваемый Permission.
     *
     * @param permission Проверяемое разрешение.
     *
     * @return True, если во время предыдущего запроса разрешения пользователь нажал отказ и не выбрал опцию "Don't ask
     * again", false - если разрешение запрашивается в первый раз, или если во время предыдущего запроса разрешения
     * пользователь выбрал опцию "Don't ask again".
     */
    private suspend fun shouldShowPermissionRationale(permission: String): Boolean = safeGetActivity().let { activity ->
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    private fun startAndObserveReturnFromScreenCustom(route: Screen): Completable {
        return screenResultObserver.observeScreenResult(route as ResultRoute<Serializable>).firstElement()
            .flatMapCompletable { Completable.complete() }.doOnSubscribe { commandExecutor.execute(Start(route)) }
    }

//    private fun <T : java.io.Serializable> startAndObserveReturnFromScreen(route: ActivityWithResultRoute<T>): Completable {
//        return screenResultObserver.observeScreenResult(route).firstElement()
//            .flatMapCompletable { Completable.complete() }
//            .doOnSubscribe { commandExecutor.execute(StartForResult(route)) }
//    }

    private suspend fun safeGetActivity(): Activity {
        return activeActivityHolder.activityFlow.single()
    }

    private companion object {
        const val GRANTED_PREFIX = "granted"
    }


}
