package ru.alexeyoss.foodie.activity.domain.repositories

import android.location.Location
import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.foodie.activity.domain.entities.UiLocationInfo

interface LocationRepository {
    suspend fun getCityNameByCoords(location: Location): Flow<Container<UiLocationInfo>>
    suspend fun getDefaultCityName(): Flow<Container<UiLocationInfo>>
}