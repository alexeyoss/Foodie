package ru.alexeyoss.foodie.activity.toolbar

import android.location.Location
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo

sealed interface MainActivityLocationUiStates {
    object Initial : MainActivityLocationUiStates
    object Loading : MainActivityLocationUiStates

    data class Success(val uiLocationInfo: UiLocationInfo) : MainActivityLocationUiStates
    object Error : MainActivityLocationUiStates
}