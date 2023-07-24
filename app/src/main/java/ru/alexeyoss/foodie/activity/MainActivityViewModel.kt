package ru.alexeyoss.foodie.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor() : ViewModel() {


    class Factory @Inject constructor() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel() as T
        }
    }
}