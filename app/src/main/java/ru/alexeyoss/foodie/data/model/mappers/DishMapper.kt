package ru.alexeyoss.foodie.data.model.mappers

import ru.alexeyoss.foodie.data.model.network.DishDTO
import ru.alexeyoss.foodie.data.model.ui.UiDish

class DishMapper : BaseMapper<UiDish, DishDTO> {
    override fun mapToDomainModel(foreignModel: DishDTO): UiDish {
        return UiDish(
            dish = foreignModel,
            isFavorite = false,
            isInCart = false
        )
    }

    override fun mapToForeignModel(domainModel: UiDish): DishDTO {
        return DishDTO(
            id = domainModel.dish.id,
            name = domainModel.dish.name,
            price = domainModel.dish.price,
            weight = domainModel.dish.weight,
            description = domainModel.dish.description,
            imageUrl = domainModel.dish.imageUrl,
            tegs = domainModel.dish.tegs
        )
    }

}