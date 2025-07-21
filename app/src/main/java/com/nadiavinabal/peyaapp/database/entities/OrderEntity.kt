package com.nadiavinabal.peyaapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderData: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int
)