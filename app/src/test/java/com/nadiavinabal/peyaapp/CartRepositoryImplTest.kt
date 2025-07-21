package com.nadiavinabal.peyaapp

import com.nadiavinabal.peyaapp.database.dao.CartDao
import com.nadiavinabal.peyaapp.database.dao.OrderHistoryDao
import com.nadiavinabal.peyaapp.database.dao.OrderItemDao
import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.database.entities.CartItemWithProductEntity
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import com.nadiavinabal.peyaapp.database.entities.ProductEntity
import com.nadiavinabal.peyaapp.domain.repository.CartRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import kotlin.test.Test
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryImplTest {

    private lateinit var cartDao: CartDao
    private lateinit var orderDao: OrderHistoryDao
    private lateinit var orderItemDao: OrderItemDao
    private lateinit var repository: CartRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        cartDao = mock(CartDao::class.java)
        orderDao = mock(OrderHistoryDao::class.java)
        orderItemDao = mock(OrderItemDao::class.java)

        repository = CartRepositoryImpl(cartDao, orderDao, orderItemDao)
    }

    @Test
    fun `getCartItems returns flow of cart items with products`() = runTest(testDispatcher) {
        // Arrange
        val cartItems = listOf(
            CartItemWithProductEntity(
                cartItemEntity = CartItemEntity(id = 1, productId = "123", quantity = 2),
                product = ProductEntity(
                    id = "123",
                    productName = "Tacos",
                    description = "Delicious",
                    imageUrl = "https://example.com/tacos.jpg",
                    price = 50.0,
                    hasDrink = false
                )
            )
        )

        whenever(cartDao.getCartItemsWithProducts()).thenReturn(flowOf(cartItems))

        // Act & Assert
        repository.getCartItems().collect { result ->
            assertEquals(1, result.size)
            assertEquals("Tacos", result.first().product.productName)
        }
    }

    @Test
    fun `addToCart inserts new item if it doesn't exist`() = runTest(testDispatcher) {
        val productId = "123"
        whenever(cartDao.getCartItemByProductId(productId)).thenReturn(null)

        repository.addToCart(productId)

        verify(cartDao).insertCartItem(CartItemEntity(productId = productId, quantity = 1))
    }

    @Test
    fun `addToCart updates quantity if item exists`() = runTest(testDispatcher) {
        val productId = "123"
        val existingItem = CartItemEntity(id = 1, productId = productId, quantity = 2)

        whenever(cartDao.getCartItemByProductId(productId)).thenReturn(existingItem)

        repository.addToCart(productId)

        verify(cartDao).updateCartItem(existingItem.copy(quantity = 3))
    }

    @Test
    fun `removeFromCart calls deleteCartItem with correct item`() = runTest(testDispatcher) {
        val cartItem = CartItemEntity(id = 1, productId = "abc", quantity = 1)

        repository.removeFromCart(cartItem)

        verify(cartDao).deleteCartItem(cartItem)
    }

    @Test
    fun `updateQuantity updates item if quantity positive`() = runTest(testDispatcher) {
        val cartItem = CartItemEntity(id = 1, productId = "abc", quantity = 2)

        repository.updateQuantity(cartItem, 5)

        verify(cartDao).updateCartItem(cartItem.copy(quantity = 5))
    }

    @Test
    fun `updateQuantity deletes item if quantity is zero`() = runTest(testDispatcher) {
        val cartItem = CartItemEntity(id = 1, productId = "abc", quantity = 2)

        repository.updateQuantity(cartItem, 0)

        verify(cartDao).deleteCartItem(cartItem)
    }

    @Test
    fun `checkout creates order and clears cart`() = runTest(testDispatcher) {
        val cartItems = listOf(
            CartItemWithProductEntity(
                cartItemEntity = CartItemEntity(id = 1, productId = "123", quantity = 2),
                product = ProductEntity(
                    id = "123",
                    productName = "Tacos",
                    description = "Delicious",
                    imageUrl = "https://example.com/tacos.jpg",
                    price = 50.0,
                    hasDrink = false
                )
            )
        )

        whenever(cartDao.getCartItemsOnce()).thenReturn(cartItems)
        whenever(orderDao.insertOrder(any(OrderEntity::class.java))).thenReturn(1L)

        repository.checkout()

        verify(orderDao).insertOrder(any(OrderEntity::class.java))
        verify(orderItemDao).insertOrderItems(any())
        verify(cartDao).clearCart()
    }
}