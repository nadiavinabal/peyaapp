package com.nadiavinabal.peyaapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.database.entities.CartItemWithProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: String): CartItemEntity?

    @Transaction
    @Query("SELECT * FROM cart_items")
    fun getCartItemsWithProducts(): Flow<List<CartItemWithProductEntity>>

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalItems(): Flow<Int>

    @Query("SELECT SUM(p.price * c.quantity) FROM cart_items c INNER JOIN products p ON c.productId = p.id")
    fun getTotalPrice(): Flow<Double>

    @Transaction
    @Query("SELECT * FROM cart_items")
    suspend fun getCartItemsOnce(): List<CartItemWithProductEntity>

}