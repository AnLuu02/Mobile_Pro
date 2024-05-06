package com.example.jetpackcomposedemo.data.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val ipv4Address = "192.168.136.1";
    private const val port = "8080";
    private const val BASE_URL = "http://$ipv4Address:$port/";

    private val intercepted: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        Log.e("Retrofit",level.toString())
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(intercepted)
        .build()

    val apiService: ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(client)
        .build().create(ApiService::class.java)
}