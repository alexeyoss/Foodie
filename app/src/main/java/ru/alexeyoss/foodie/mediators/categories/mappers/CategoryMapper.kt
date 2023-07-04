package ru.alexeyoss.foodie.mediators.categories.mappers

import ru.alexeyoss.network.categories.models.CategoryDTO
import ru.alexeyoss.features.categories.domain.entities.UiCategory
import ru.alexeyoss.core.common.BaseMapper
import javax.inject.Inject

class CategoryMapper
    @Inject constructor(): BaseMapper<UiCategory, CategoryDTO> {
    override fun mapToDomainModel(foreignModel: CategoryDTO): UiCategory {
        return UiCategory(
            id = foreignModel.id,
            name = foreignModel.name,
            image_url = foreignModel.image_url,
        )
    }

    override fun mapToForeignModel(domainModel: UiCategory): CategoryDTO {
        return CategoryDTO(
            id = domainModel.id,
            name = domainModel.name,
            image_url = domainModel.image_url
        )
    }
}