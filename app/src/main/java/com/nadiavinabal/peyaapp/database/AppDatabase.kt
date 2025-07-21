package com.nadiavinabal.peyaapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadiavinabal.peyaapp.database.dao.CartDao
import com.nadiavinabal.peyaapp.database.dao.OrderHistoryDao
import com.nadiavinabal.peyaapp.database.dao.OrderItemDao
import com.nadiavinabal.peyaapp.database.dao.ProductDao
import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import com.nadiavinabal.peyaapp.database.entities.OrderItemEntity
import com.nadiavinabal.peyaapp.database.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, CartItemEntity::class, OrderEntity::class, OrderItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderHistoryDao(): OrderHistoryDao
    abstract fun orderItemDao(): OrderItemDao
}