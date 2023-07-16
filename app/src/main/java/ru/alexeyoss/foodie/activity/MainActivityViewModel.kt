package ru.alexeyoss.foodie.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.alexeyoss.foodie.permission.LocationPermissionRequest
import ru.alexeyoss.services.permission.PermissionManager
import javax.inject.Inject
import javax.inject.Provider

class MainActivityViewModel
@Inject constructor(
    private val permissionManager: PermissionManager
) : ViewModel() {


    private val _permStatus = MutableLiveData<Boolean>()
    val permStatus: LiveData<Boolean> = _permStatus

    init {
        viewModelScope.launch(Dispatchers.Main) {
            permissionManager.request(LocationPermissionRequest())
                .collect { grantResult ->
                    _permStatus.value = grantResult
                }
        }
    }


    class Factory @Inject constructor(
        private val permissionManager: Provider<PermissionManager>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(permissionManager.get()) as T
        }
    }
}