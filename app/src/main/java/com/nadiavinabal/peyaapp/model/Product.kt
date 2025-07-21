package com.nadiavinabal.peyaapp.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val hasDrink: Boolean,
    val description: String
)