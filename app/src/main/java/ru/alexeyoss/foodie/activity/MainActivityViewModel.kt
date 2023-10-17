package ru.alexeyoss.foodie.activity

import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeyoss.foodie.activity.domain.GetCityNameByCoords
import ru.alexeyoss.foodie.activity.domain.GetDefaultCityName
import ru.alexeyoss.foodie.activity.toolbar.MainActivityLocationUiStates
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.permissions.PermissionEvent
import ru.alexeyoss.foodie.permissions.PermissionManager
import ru.alexeyoss.foodie.permissions.PermissionStatus
import ru.alexeyoss.foodie.permissions.request.LocationPermissionRequest
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val getCityNameByCoords: GetCityNameByCoords,
    private val getDefaultCityName: GetDefaultCityName,
    private val permissionManager: PermissionManager
) : ViewModel() {

    private val _toolbarLocationStateFlow =
        MutableStateFlow<MainActivityLocationUiStates>(MainActivityLocationUiStates.Initial)
    val toolbarLocationStateFlow = _toolbarLocationStateFlow.asStateFlow()

    private val _mainActivitySideEffects = MutableStateFlow<MainActivitySideEffects>(
        MainActivitySideEffects.Initial
    )
    val mainActivitySideEffects = _mainActivitySideEffects

    init {
        observePermissionEvents()
    }

    @SuppressLint("MissingPermission")
    fun checkPermissionStatus() {
        viewModelScope.launch {
            if (!permissionManager.checkPermissionsStatus(LocationPermissionRequest)) {
                permissionManager.request(LocationPermissionRequest)
            } else {
                getLastKnownLocation()
            }
        }
    }
    fun onPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModelScope.launch { permissionManager.onPermissionResult(requestCode, permissions, grantResults) }
    }

    @RequiresPermission(
        anyOf = [
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        ]
    )
    private fun getLastKnownLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            getCityNameByCoords.invoke().collect { container ->
                when (container) {
                    is Container.Error -> _toolbarLocationStateFlow.emit(
                        MainActivityLocationUiStates.Error
                    )

                    is Container.Loading -> _toolbarLocationStateFlow.emit(
                        MainActivityLocationUiStates.Loading
                    )

                    is Container.Success -> _toolbarLocationStateFlow.emit(
                        MainActivityLocationUiStates.Success(container.value)
                    )
                }
            }
        }
    }

    private fun setDefaultCityName() {
        viewModelScope.launch(Dispatchers.IO) {
            getDefaultCityName.invoke().collect { container ->
                when (container) {
                    is Container.Error -> Unit
                    is Container.Loading -> Unit
                    is Container.Success -> _toolbarLocationStateFlow.emit(
                        MainActivityLocationUiStates.Success(container.value)
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getPermissionStatus(permissionStatus: PermissionStatus) {
        when (permissionStatus) {
            PermissionStatus.GRANTED -> getLastKnownLocation()
            PermissionStatus.DENIED -> permissionManager.request(LocationPermissionRequest)
            PermissionStatus.DENIED_FOREVER -> {
                setDefaultCityName()
                mainActivitySideEffects.emit(MainActivitySideEffects.ShowPermissionRational)
            }
        }
    }

    private fun observePermissionEvents() {
        viewModelScope.launch {
            permissionManager.permissionEventEmitter.collect { permissionEvent ->
                when (permissionEvent) {
                    is PermissionEvent.Success -> getPermissionStatus(permissionEvent.permissionStatus)
                    is PermissionEvent.Error -> Unit
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val getCityNameByCoords: Lazy<GetCityNameByCoords>,
        private val getDefaultCityName: Lazy<GetDefaultCityName>,
        private val permissionManager: Lazy<PermissionManager>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(
                getCityNameByCoords.get(),
                getDefaultCityName.get(),
                permissionManager.get()
            ) as T
        }
    }
}
