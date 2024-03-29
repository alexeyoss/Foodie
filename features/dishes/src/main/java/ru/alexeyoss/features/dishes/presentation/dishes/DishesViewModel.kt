package ru.alexeyoss.features.dishes.presentation.dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeyoss.features.dishes.domain.ChangeFilterUseCase
import ru.alexeyoss.features.dishes.domain.GenerateDishListStateUseCase
import ru.alexeyoss.features.dishes.domain.GetDishesUseCase
import ru.alexeyoss.features.dishes.domain.models.DishListState
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO
import ru.alexeyoss.features.dishes.presentation.DishesSideEffects
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import timber.log.Timber
import javax.inject.Inject

class DishesViewModel
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @CoroutinesModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getDishesUseCase: GetDishesUseCase,
    private val generateDishListStateUseCase: GenerateDishListStateUseCase,
    private val changeFilterUseCase: ChangeFilterUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.i(throwable) }

    private val _sideEffects: MutableStateFlow<DishesSideEffects> = MutableStateFlow(DishesSideEffects.Initial)
    val sideEffects = _sideEffects.asStateFlow()

    private val _dishListState: MutableLiveData<DishListState> = MutableLiveData()
    val dishListState: LiveData<DishListState> = _dishListState

    init {
        initDishListState()
    }

    private fun initDishListState() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getDishesUseCase.invoke().collect { container ->
                when (container) {
                    is Container.Error -> _sideEffects.emit(DishesSideEffects.Error(container.exception))
                    is Container.Loading -> _sideEffects.emit(DishesSideEffects.Loading)
                    is Container.Success -> {
                        val newDishListState = generateDishListStateUseCase.invoke(container.extract())
                        withContext(mainDispatcher) {
                            _dishListState.value = newDishListState
                        }
                    }
                }
            }
        }
    }

    fun newFilterSelected(uiFilterDTO: UiFilterDTO) {
        // TODO use viewmodel savedstatehandle for prevent process kill by Android OS
        val lastDishListState = dishListState.value

        if (lastDishListState == null) {
            initDishListState()
        } else {
            viewModelScope.launch(defaultDispatcher + exceptionHandler) {
                val newDishListState = changeFilterUseCase.invoke(lastDishListState, uiFilterDTO)
                withContext(mainDispatcher) {
                    _dishListState.value = newDishListState
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
        @CoroutinesModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher,
        private val getDishesUseCase: GetDishesUseCase,
        private val generateDishListStateUseCase: GenerateDishListStateUseCase,
        private val changeFilterUseCase: ChangeFilterUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DishesViewModel::class.java)
            return DishesViewModel(
                ioDispatcher,
                defaultDispatcher,
                mainDispatcher,
                getDishesUseCase,
                generateDishListStateUseCase,
                changeFilterUseCase
            ) as T
        }
    }
}
