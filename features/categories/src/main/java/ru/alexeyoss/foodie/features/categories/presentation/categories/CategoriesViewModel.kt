package ru.alexeyoss.foodie.features.categories.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.features.categories.domain.GetCategoriesUseCase
import ru.alexeyoss.foodie.features.categories.presentation.CategoriesUiState
import timber.log.Timber
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    private val _categoriesFlow: MutableStateFlow<CategoriesUiState> = MutableStateFlow(CategoriesUiState.Initial)
    val categoriesFlow = _categoriesFlow.asStateFlow()

    fun getCategories() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getCategoriesUseCase.invoke().collect { container ->
                when (container) {
                    is Container.Error -> {
                        _categoriesFlow.emit(
                            CategoriesUiState.Error(container.exception)
                        )
                    }

                    is Container.Loading -> _categoriesFlow.emit(CategoriesUiState.Loading)

                    is Container.Success -> {
                        _categoriesFlow.emit(
                            CategoriesUiState.Success(container.extract())
                        )
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        @CoroutinesModule.IoDispatcher private val ioDispatcher: Lazy<CoroutineDispatcher>,
        private val getCategoriesUseCase: Lazy<GetCategoriesUseCase>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CategoriesViewModel::class.java)
            return CategoriesViewModel(ioDispatcher.get(), getCategoriesUseCase.get()) as T
        }
    }
}
