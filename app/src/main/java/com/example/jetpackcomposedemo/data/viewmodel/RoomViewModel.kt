package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Room
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _list = MutableLiveData<Resource<List<Room>>>()
    val list: LiveData<Resource<List<Room>>> = _list
    private var isCallApi_getListRoom = false

    fun getListRoom() {
        if (!isCallApi_getListRoom) {
            _list.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getAllRoom()
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.success(response.body()))
                    }
                    isCallApi_getListRoom = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
    }
}