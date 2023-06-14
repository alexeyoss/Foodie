package ru.alexeyoss.features.categories.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.alexeyoss.foodie.data.network.ResponseStates
import ru.alexeyoss.foodie.di.CoroutinesModule
import ru.alexeyoss.foodie.domain.MainRepository
import ru.alexeyoss.foodie.redux.ApplicationState
import ru.alexeyoss.foodie.redux.Store
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepository: MainRepository,
    val store: Store<ApplicationState>
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    fun getCategories() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            mainRepository.getCategories().let { responseState ->
                when (responseState) {
                    is ResponseStates.Success -> {
                        store.update { applicationState ->
                            return@update applicationState.copy(
                                UiCategories = responseState.data
                            )
                        }
                    }

                    else -> Unit // TODO  error handling
                }
            }
        }
    }
}