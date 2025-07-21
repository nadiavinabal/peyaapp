package com.nadiavinabal.peyaapp.domain.repository

import com.nadiavinabal.peyaapp.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertInitialProducts()
    fun getAllProducts(): Flow<List<Product>>
}