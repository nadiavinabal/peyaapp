package com.nadiavinabal.peyaapp.data.datasource

import com.nadiavinabal.peyaapp.model.Product

interface ProductDataSource {
    suspend fun getAllProducts(): List<Product>
}