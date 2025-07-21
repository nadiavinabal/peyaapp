package com.nadiavinabal.peyaapp.domain.repository

import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.database.entities.CartItemWithProductEntity
import com.nadiavinabal.peyaapp.database.entities.ProductEntity
import com.nadiavinabal.peyaapp.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemWithProductEntity>>
    suspend fun addToCart(productId: String)
    suspend fun removeFromCart(cartItem: CartItemEntity)
    suspend fun updateQuantity(cartItem: CartItemEntity, newQuantity: Int)
    suspend fun clearCart()
    suspend fun checkout()
    fun getTotalItems(): Flow<Int>
    fun getTotalPrice(): Flow<Double>
}