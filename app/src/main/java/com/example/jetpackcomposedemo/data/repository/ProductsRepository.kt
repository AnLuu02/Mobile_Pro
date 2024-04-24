package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.helpper.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductsRepository {
    suspend fun getProductsList(): Flow<Result<List<Product>>>
    suspend fun postProductData(product: Product): Response<Product>
}