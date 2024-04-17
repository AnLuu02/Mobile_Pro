package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jetpackcomposedemo.data.repository.ProductsRepository

class ProductsViewModelFactory(private val ProductsRepository: ProductsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductsViewModel(ProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}