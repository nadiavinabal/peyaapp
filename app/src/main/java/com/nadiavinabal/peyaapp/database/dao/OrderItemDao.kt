package com.nadiavinabal.peyaapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nadiavinabal.peyaapp.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItemEntity>)

    @Update
    suspend fun updateOrderItem(orderItem: OrderItemEntity)

    @Query("DELETE FROM order_items WHERE orderItemId = :orderItemId")
    suspend fun deleteOrderItem(orderItemId: Int)

    @Query("SELECT * FROM order_items")
    fun getAllOrderItem(): Flow<List<OrderItemEntity>>

    @Query("SELECT * FROM order_items WHERE orderItemId = :orderItemId")
    suspend fun getOrderItemById(orderItemId: Int): OrderItemEntity?
}
