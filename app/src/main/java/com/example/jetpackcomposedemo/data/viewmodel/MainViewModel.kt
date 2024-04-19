package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.helpper.Result
import com.example.jetpackcomposedemo.data.models.coupon.Coupon
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class MainViewModel(

    private val couponRepository: CouponRepository
):ViewModel() {

    private val _product = MutableStateFlow<List<Coupon>>(emptyList())
    val product = _product.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            couponRepository.getCouponList().collectLatest { result->
                when(result){
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)

                    }
                    is Result.Success -> {
                        result.data?.let {products->
                            _product.update { products }
                        }

                    }
                }
            }
        }
    }
}