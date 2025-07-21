package com.nadiavinabal.peyaapp.data.api

import com.nadiavinabal.peyaapp.model.Product
import retrofit2.http.GET

interface FoodApiService {
    @GET("foods")
    suspend fun getFoods(): List<Product>
}