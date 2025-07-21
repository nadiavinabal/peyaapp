package com.nadiavinabal.peyaapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CartItemWithProductEntity(
    @Embedded val cartItemEntity: CartItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)