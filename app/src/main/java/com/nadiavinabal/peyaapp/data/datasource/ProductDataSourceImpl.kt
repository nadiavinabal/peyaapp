package com.nadiavinabal.peyaapp.data.datasource

import com.nadiavinabal.peyaapp.data.api.FoodApiService
import javax.inject.Inject
import com.nadiavinabal.peyaapp.model.Product

class ProductDataSourceImpl @Inject constructor(
    private val apiService: FoodApiService
) : ProductDataSource {
    override suspend fun getAllProducts(): List<Product> {
        return apiService.getFoods()
    }
}