package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.RoomType
import com.example.jetpackcomposedemo.data.repository.RoomTypeRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch

class RoomTypeViewModel(private val repository: RoomTypeRepository) : ViewModel() {
    private val _list = MutableLiveData<Resource<List<RoomType>>>()
    val list: LiveData<Resource<List<RoomType>>> = _list
    private var isCallApi_getListRoom = false

    fun getListRoomType() {
        if (!isCallApi_getListRoom) {
            _list.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getAllRoomType()
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