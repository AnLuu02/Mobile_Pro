package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Identity
import com.example.jetpackcomposedemo.data.models.POST_Body_UserCoupon
import com.example.jetpackcomposedemo.data.models.User
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.data.repository.UserRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository) : ViewModel() { // sửa repository
    private val _list = MutableLiveData<Resource<List<User>>>() // sửa class
    val list: LiveData<Resource<List<User>>> = _list // sửa class
    private var isCallApi_getUser = false
    private var isCallApi_updateUserPoint = false
    private var isCallApi_updateUserRollUp = false

    fun getUser(phoneNumber: String) {
        if (!isCallApi_getUser) {
            _list.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getUser(phoneNumber) // // sửa function
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isCallApi_getUser = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }

    fun updateUserPoint(userId: Int, point: Int) {
        if(!isCallApi_updateUserRollUp) {
            viewModelScope.launch {
                try {
                    val response = repository.updateUserPoint(userId, point) // // sửa function
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isCallApi_updateUserRollUp = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }

    fun updateUserRollUp(userId: Int) {
        if(!isCallApi_updateUserPoint) {
            viewModelScope.launch {
                try {
                    val response = repository.updateUserRollUp(userId) // // sửa function
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isCallApi_updateUserPoint = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }
}