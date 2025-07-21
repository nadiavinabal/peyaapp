package com.nadiavinabal.peyaapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import com.nadiavinabal.peyaapp.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Update
    suspend fun updateOrders(orderItem: OrderItemEntity)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrder(orderId: Long)

    @Query("SELECT * FROM orders")
    fun getAllOrder(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: Long): OrderEntity?
}

