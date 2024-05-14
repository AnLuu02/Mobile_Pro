package com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposedemo.data.repository.BookingRepository

class BookingViewModelApiFactory(
    private val repository: BookingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BookingViewModelApi::class.java)) {
            BookingViewModelApi(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}