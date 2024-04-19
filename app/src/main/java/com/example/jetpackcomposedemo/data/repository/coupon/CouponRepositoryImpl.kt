package com.example.jetpackcomposedemo.data.repository.coupon

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.jetpackcomposedemo.helpper.Result
import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.models.coupon.Coupon
import com.example.jetpackcomposedemo.data.network.ApiService
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.data.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CouponRepositoryImpl (
    private val apiService: ApiService
): CouponRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCouponList(): Flow<Result<List<Coupon>>> {
        return flow {
            val coupon_rs = try {
                apiService.getCouponList()
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
            emit(Result.Success(coupon_rs))
        }
    }

}