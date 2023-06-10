package ru.alexeyoss.foodie.data

import retrofit2.http.GET
import ru.alexeyoss.foodie.BuildConfig.*
import ru.alexeyoss.foodie.data.models.CategoryList
import ru.alexeyoss.foodie.data.models.DishesList

interface ApiService {

    @GET(BASE_URL + API_VERSION + "058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryList

    @GET(BASE_URL + API_VERSION + "aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getCategoryDishes(): DishesList

}