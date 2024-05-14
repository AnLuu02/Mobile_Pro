package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Identity
import com.example.jetpackcomposedemo.data.models.POST_Body_UserCoupon
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class UserCouponViewModel(private val repository: UserCouponRepository) : ViewModel() { // sửa repository
    private val _list = MutableLiveData<Resource<List<UserCoupon>>>() // sửa class
    private val _list2 = MutableLiveData<Resource<List<Identity>>>()
    val list: LiveData<Resource<List<UserCoupon>>> = _list // sửa class
    val list2: LiveData<Resource<List<Identity>>> = _list2
    private var isCallApi_getListCouponOfUser = false
    private var isCallApi_AddUserCoupon = false

    fun getListCouponOfUser(id: String) {
        if (!isCallApi_getListCouponOfUser) {
            _list.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getListCouponOfUser(id) // // sửa function
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isCallApi_getListCouponOfUser = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }

    fun AddUserCoupon(
        CouponID: String,
        UserID: String,
        IsUsed: String = "1",
        NumberOfUses: String = "1",
        DateScan: String? = null
    ) {
        if(!isCallApi_AddUserCoupon) {
            _list2.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val post_body = POST_Body_UserCoupon(
                        CouponID = CouponID,
                        UserID = UserID,
                        IsUsed = IsUsed,
                        NumberOfUses = NumberOfUses,
                        DateScan = DateScan
                    )
                    val response = repository.postUserCoupon(post_body = post_body)
                    if (response.isSuccessful) {
                        _list2.postValue(Resource.success(response.body()))
                        // handleAfterDone
                    } else {
                        _list2.postValue(
                            Resource.error(
                                "Error ${response.code()}: ${response.message()}",
                                null
                            )
                        )
                    }
                } catch (e: Exception) {
                    _list2.postValue(
                        Resource.error(
                            "Exception: ${e.localizedMessage}",
                            null
                        )
                    )
                }
            }
        }
    }
}