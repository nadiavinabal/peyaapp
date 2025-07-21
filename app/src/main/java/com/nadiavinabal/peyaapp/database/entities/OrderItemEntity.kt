package com.nadiavinabal.peyaapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)

data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val orderItemId: Int = 0,
    val orderId: Long,
    val productId: String,
    val quantity: Int,
    val price: Double
)