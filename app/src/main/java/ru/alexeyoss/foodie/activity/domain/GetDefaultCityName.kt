package ru.alexeyoss.foodie.activity.domain

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo
import ru.alexeyoss.foodie.activity.domain.repositories.LocationRepository
import javax.inject.Inject

class GetDefaultCityName
@Inject
constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Flow<Container<UiLocationInfo>> = locationRepository.getDefaultCityName()
}