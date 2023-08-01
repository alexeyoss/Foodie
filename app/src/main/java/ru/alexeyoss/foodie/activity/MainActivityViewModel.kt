package ru.alexeyoss.foodie.activity

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeyoss.foodie.activity.toolbar.MainActivityLocationUiStates
import ru.alexeyoss.location.interactor.DefaultLocationInteractor
import ru.alexeyoss.location.interactor.DefaultLocationStates
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val defaultLocationInteractor: DefaultLocationInteractor
) : ViewModel() {

    private val _toolbarLocationStateFlow =
        MutableStateFlow<MainActivityLocationUiStates>(MainActivityLocationUiStates.Initial)
    val toolbarLocationStateFlow = _toolbarLocationStateFlow.asStateFlow()

    /**
     * Init location flow scenario
     * */
    fun getLastKnownLocation() {
        viewModelScope.launch(Dispatchers.Main) {
            defaultLocationInteractor.getLastKnownLocation().collect { defaultLocationState ->
                when (defaultLocationState) {
                    is DefaultLocationStates.Loading -> _toolbarLocationStateFlow.emit(MainActivityLocationUiStates.Loading)
                    is DefaultLocationStates.SuccessWithCurrentLocation -> {
                        _toolbarLocationStateFlow.emit(MainActivityLocationUiStates.Success(defaultLocationState.location))
                    }

                    is DefaultLocationStates.SuccessWithDefaultLocation -> {
                        _toolbarLocationStateFlow.emit(MainActivityLocationUiStates.Success(defaultLocationState.location))
                    }
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val defaultLocationInteractor: Lazy<DefaultLocationInteractor>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(
                defaultLocationInteractor.get()
            ) as T
        }
    }
}