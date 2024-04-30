package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.User.User
import com.example.jetpackcomposedemo.data.repository.UserRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<Resource<List<User>>>()
    val user: LiveData<Resource<List<User>>> = _user
    private var isApiCalledCreateUser = false

    fun createUser(user: User) {
        _user.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = repository.createUser(user)
                if (response.isSuccessful) {
                    _user.postValue(Resource.success(response.body()))
                } else {
                    _user.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                }
            } catch (e: Exception) {
                _user.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
            }
        }
    }


}