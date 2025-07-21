package com.nadiavinabal.peyaapp.model

data class CartItem(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int
)