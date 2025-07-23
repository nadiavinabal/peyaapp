package com.nadiavinabal.peyaapp.domain.repository

import android.util.Log
import com.nadiavinabal.peyaapp.database.dao.CartDao
import com.nadiavinabal.peyaapp.database.dao.OrderHistoryDao
import com.nadiavinabal.peyaapp.database.dao.OrderItemDao
import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.database.entities.CartItemWithProductEntity
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import com.nadiavinabal.peyaapp.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val orderDao: OrderHistoryDao,
    private val orderItemDao: OrderItemDao
) : CartRepository {
    override fun getCartItems(): Flow<List<CartItemWithProductEntity>> =
        cartDao.getCartItemsWithProducts()

    override suspend fun addToCart(productId: String) {
        val existingItem = cartDao.getCartItemByProductId(productId)
        if (existingItem != null) {
            cartDao.updateCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
        } else {
            cartDao.insertCartItem(CartItemEntity(productId = productId, quantity = 1))
        }
    }

    override suspend fun removeFromCart(cartItem: CartItemEntity) {
        cartDao.deleteCartItem(cartItem)
    }

    override suspend fun updateQuantity(cartItem: CartItemEntity, newQuantity: Int) {
        if (newQuantity > 0) {
            cartDao.updateCartItem(cartItem.copy(quantity = newQuantity))
        } else {
            cartDao.deleteCartItem(cartItem)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override fun getTotalItems(): Flow<Int> = cartDao.getTotalItems()

    override fun getTotalPrice(): Flow<Double> = cartDao.getTotalPrice()

    override suspend fun checkout() {
        val cartItems = cartDao.getCartItemsOnce()
        if (cartItems.isEmpty()) return

        val totalAmount = cartItems.sumOf { it.product.price * it.cartItemEntity.quantity }
        val totalItems = cartItems.sumOf { it.cartItemEntity.quantity }

        // Crear nueva orden
        val orderId = orderDao.insertOrder(
            OrderEntity(
                totalAmount = totalAmount,
                totalItems = totalItems
            )
        )

        // Insertar los items en la orden
        val orderItems = cartItems.map {
            OrderItemEntity(
                orderId = orderId,
                productId = it.product.id,
                quantity = it.cartItemEntity.quantity,
                price = it.product.price
            )
        }

        orderItemDao.insertOrderItems(orderItems)

        cartDao.clearCart()
    }
}



