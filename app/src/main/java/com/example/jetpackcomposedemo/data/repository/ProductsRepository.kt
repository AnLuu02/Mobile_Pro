package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.helpper.Result
import com.example.jetpackcomposedemo.data.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList(): Flow<Result<List<Product>>>
}