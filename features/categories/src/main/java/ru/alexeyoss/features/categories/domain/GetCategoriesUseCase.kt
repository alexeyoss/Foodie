package ru.alexeyoss.features.categories.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.core.common.CoroutinesModule
import ru.alexeyoss.core.common.BaseUseCase
import ru.alexeyoss.features.categories.domain.entities.UiCategory
import ru.alexeyoss.features.categories.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategories1UseCase
@Inject constructor(
    @CoroutinesModule.IoDispatcher val ioDispatcher: CoroutineDispatcher,
    private val categoriesRepository: CategoriesRepository
) : BaseUseCase(ioDispatcher) {


    suspend operator fun invoke(): Flow<Container<List<UiCategory>>> =
        with(scope.coroutineContext + exceptionHandler) {
            return categoriesRepository.getCategories()
        }

}