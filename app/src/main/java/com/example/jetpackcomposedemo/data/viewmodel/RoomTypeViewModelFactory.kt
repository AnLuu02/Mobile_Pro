package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposedemo.data.repository.RoomTypeRepository

class RoomTypeViewModelFactory(
    private val repository: RoomTypeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RoomTypeViewModel::class.java)) {
            RoomTypeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}