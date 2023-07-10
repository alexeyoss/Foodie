package ru.alexeyoss.features.cart.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CartViewModel : ViewModel() {


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CartViewModel::class.java)
            return CartViewModel() as T
        }
    }
}