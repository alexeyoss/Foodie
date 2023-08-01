package ru.alexeyoss.foodie.activity.toolbar

import android.location.Location

sealed interface MainActivityLocationUiStates {
    object Initial : MainActivityLocationUiStates
    object Loading : MainActivityLocationUiStates

    data class Success(val location: Location) : MainActivityLocationUiStates
    object Error : MainActivityLocationUiStates
}