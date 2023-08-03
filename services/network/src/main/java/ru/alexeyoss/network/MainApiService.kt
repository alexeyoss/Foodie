package ru.alexeyoss.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.alexeyoss.network.models.requests.LocationRequest
import ru.alexeyoss.network.models.responses.CategoryListDTO
import ru.alexeyoss.network.models.responses.DishesListDTO
import ru.alexeyoss.network.models.responses.LocationDTO

/**
 * Base remote source
 * */
interface MainApiService {


    @GET("49578eea-ff44-4b8f-8893-525e7f6a6e35")
    suspend fun getCategories(): CategoryListDTO

    @GET("ba255bce-3079-45ac-83db-e38cd4be1c95")
    suspend fun getDishes(): DishesListDTO

    @POST("b1388e46-a3c1-44ca-b42f-5f659bf6c5e6")
    suspend fun getCityNameByCoords(
        @Body locationRequest: LocationRequest
    ): LocationDTO
}