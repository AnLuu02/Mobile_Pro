package com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _roomList = MutableLiveData<Resource<List<Room>>>()

    val roomList: LiveData<Resource<List<Room>>> = _roomList
    init {
        getRoomList()
    }

    fun getRoomList() {
        viewModelScope.launch {
            _roomList.postValue(Resource.loading(null))
            try {
                val response = repository.getRooms()
                if (response.isSuccessful) {
                    _roomList.postValue(Resource.success(response.body()))
                } else {
                    _roomList.postValue(Resource.error(response.errorBody().toString(), null))
                }
            } catch (e: Exception) {
                _roomList.postValue(Resource.error(e.toString(), null))
            }
        }
    }


    private val _rooms = MutableLiveData<Resource<List<Room>>>()
    val rooms: LiveData<Resource<List<Room>>> = _rooms
    fun getRoomById(id: String) {
        _rooms.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = repository.getRoomById(id)
                if (response.isSuccessful) {
                    _rooms.postValue(Resource.success(response.body()))
                } else {
                    _rooms.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                }
            } catch (e: Exception) {
                _rooms.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
            }
        }
    }

}