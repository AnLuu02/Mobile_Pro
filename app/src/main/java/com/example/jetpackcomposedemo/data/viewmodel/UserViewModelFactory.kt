package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.data.repository.UserRepository

class UserViewModelFactory(
    private val repository: UserRepository // sửa repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserViewModel::class.java)) { // sửa ViewModel
            UserViewModel(repository) as T // sửa ViewModel
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}