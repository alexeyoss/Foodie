package ru.alexeyoss.foodie.activity

import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.foodie.activity.domain.GetCityNameByCoords
import ru.alexeyoss.foodie.activity.toolbar.MainActivityLocationUiStates
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val getCityNameByCoords: GetCityNameByCoords
) : ViewModel() {
    private val _toolbarLocationStateFlow =
        MutableStateFlow<MainActivityLocationUiStates>(MainActivityLocationUiStates.Initial)
    val toolbarLocationStateFlow = _toolbarLocationStateFlow.asStateFlow()

    @RequiresPermission(
        anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"]
    )
    fun getLastKnownLocation() {
        viewModelScope.launch {
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


    class Factory @Inject constructor(
        private val getCityNameByCoords: Lazy<GetCityNameByCoords>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(
                getCityNameByCoords.get()
            ) as T
        }
    }
}
