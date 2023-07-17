package ru.alexeyoss.foodie.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.alexeyoss.services.permission.PermissionManager
import ru.alexeyoss.services.permission.PermissionRequest
import javax.inject.Inject
import javax.inject.Provider


// TODO DEBUG
class MainActivityViewModel
@Inject constructor(
    private val permissionManager: PermissionManager
) : ViewModel() {

    private val _permissionStatus = MutableLiveData<Boolean>()
    val permissionStatus: LiveData<Boolean> = _permissionStatus

    fun requestPermission(permissionRequest: PermissionRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            permissionManager.request(permissionRequest).onEach { grantResult ->
                _permissionStatus.value = grantResult
            }.launchIn(viewModelScope)
        }
    }


    // TODO get rid of boilerplate viewModel code
    class Factory @Inject constructor(
        private val permissionManager: Provider<PermissionManager>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(permissionManager.get()) as T
        }
    }
}