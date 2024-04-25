package com.example.jetpackcomposedemo.data.models

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)