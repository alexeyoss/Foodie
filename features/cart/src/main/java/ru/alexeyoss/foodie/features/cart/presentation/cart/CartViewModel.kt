package ru.alexeyoss.foodie.features.cart.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.features.cart.domain.GetCartItems
import ru.alexeyoss.foodie.features.cart.domain.UpdateUiCartState
import ru.alexeyoss.foodie.features.cart.domain.entities.UiCartState
import ru.alexeyoss.foodie.features.cart.presentation.CartSideEffects
import ru.alexeyoss.foodie.features.cart.presentation.cart.adapter.QuantityOperation
import timber.log.Timber
import javax.inject.Inject

class CartViewModel
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @CoroutinesModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getCartItems: GetCartItems,
    private val generateUiCartScreenState: UpdateUiCartState
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    private val _sideEffects: MutableStateFlow<CartSideEffects> = MutableStateFlow(CartSideEffects.Initial)
    val sideEffects = _sideEffects.asStateFlow()

    private val _uiCartScreenState: MutableLiveData<UiCartState> = MutableLiveData()
    val uiCartScreenState: LiveData<UiCartState> = _uiCartScreenState


    fun initUiCartState() {
        /**
         * IDEA get lastCartState from local persistent storage (define 24 hour cart lifetime, drop by init operations)
         * */
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getCartItems.invoke().collect { container ->
                when (container) {
                    is Container.Error -> _sideEffects.emit(CartSideEffects.Error(container.exception))
                    is Container.Loading -> _sideEffects.emit(CartSideEffects.Loading)
                    is Container.Success -> {
                        val newUiCartState = generateUiCartScreenState.invoke(container.value)
                        withContext(mainDispatcher) {
                            _uiCartScreenState.value = newUiCartState
                        }
                    }
                }
            }
        }
    }

    fun updateUiCartState(quantityOperation: QuantityOperation) {
        val lastUiCartState = _uiCartScreenState.value?.cartItems

        if (lastUiCartState == null) {
            initUiCartState()
        } else {
            viewModelScope.launch(exceptionHandler) {
                val newUiCartState = generateUiCartScreenState(lastUiCartState, quantityOperation)
                withContext(mainDispatcher) {
                    _uiCartScreenState.value = newUiCartState
                }
            }
        }

    }

    class Factory @Inject constructor(
        @CoroutinesModule.IoDispatcher private val ioDispatcher: Lazy<CoroutineDispatcher>,
        @CoroutinesModule.MainDispatcher private val mainDispatcher: Lazy<CoroutineDispatcher>,
        private val getCartItems: GetCartItems,
        private val generateUiCartScreenState: UpdateUiCartState
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CartViewModel::class.java)
            return CartViewModel(
                ioDispatcher.get(),
                mainDispatcher.get(),
                getCartItems,
                generateUiCartScreenState
            ) as T
        }
    }
}