package ru.alexeyoss.foodie.mediators.dishes.mappers

import ru.alexeyoss.core.common.BaseMapper
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.network.dishes.models.DishDTO

class DishesMapper : BaseMapper<UiDishDTO, DishDTO> {
    override fun mapToDomainModel(foreignModel: DishDTO): UiDishDTO {
        return UiDishDTO(
            id = foreignModel.id,
            name = foreignModel.name,
            price = foreignModel.price,
            weight = foreignModel.weight,
            description = foreignModel.description,
            imageUrl = foreignModel.imageUrl,
            tegs = foreignModel.tegs
        )
    }

    override fun mapToForeignModel(domainModel: UiDishDTO): DishDTO {
        return DishDTO(
            id = domainModel.id,
            name = domainModel.name,
            price = domainModel.price,
            weight = domainModel.weight,
            description = domainModel.description,
            imageUrl = domainModel.imageUrl,
            tegs = domainModel.tegs
        )
    }

}