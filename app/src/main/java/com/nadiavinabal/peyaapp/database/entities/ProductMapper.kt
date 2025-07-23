package com.nadiavinabal.peyaapp.database.entities

import com.nadiavinabal.peyaapp.model.Product

fun ProductEntity.toDomain() = Product(
    id, productName, price, imageUrl, category, hasDrink, description
)

fun Product.toEntity() = ProductEntity(
    _id, name, price, imageUrl, category, hasDrink, description
)