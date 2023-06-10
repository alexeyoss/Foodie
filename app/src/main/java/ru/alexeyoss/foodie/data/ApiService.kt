package ru.alexeyoss.foodie.data

import retrofit2.http.GET
import ru.alexeyoss.foodie.BuildConfig.*
import ru.alexeyoss.foodie.data.models.CategoryList

interface ApiService {

    @GET(BASE_URL + API_VERSION + "058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryList
}