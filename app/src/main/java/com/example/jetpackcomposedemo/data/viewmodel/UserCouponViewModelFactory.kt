package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository

class UserCouponViewModelFactory(
    private val repository: UserCouponRepository // sửa repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserCouponViewModel::class.java)) { // sửa ViewModel
            UserCouponViewModel(repository) as T // sửa ViewModel
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}