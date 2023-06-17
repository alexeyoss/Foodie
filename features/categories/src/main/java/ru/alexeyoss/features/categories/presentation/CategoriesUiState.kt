package ru.alexeyoss.features.categories.presentation

sealed interface CategoriesUiState {
    data class Success<T>(val data: T) : CategoriesUiState

    // TODO estimate is it correct
    data class Error(val error: Exception) : CategoriesUiState
    object Loading : CategoriesUiState
    object Initial : CategoriesUiState
}