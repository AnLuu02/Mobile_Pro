package com.example.jetpackcomposedemo.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.jetpackcomposedemo.helpper.Result
import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ProductsRepositoryImpl (
    private val apiService: ApiService
): ProductsRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getProductsList(): Flow<Result<List<Product>>> {
        return flow {
            val productsFromApi = try {
                apiService.getProductsList()
            } catch (e:IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading product"))
                return@flow
            } catch (e:HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading product"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading product"))
                return@flow
            }
            emit(Result.Success(productsFromApi.products))
        }
    }

}