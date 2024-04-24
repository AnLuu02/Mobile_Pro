package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.repository.ProductsRepository
import com.example.jetpackcomposedemo.helpper.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.*


class ProductsViewModel(
    private val productsRepository: ProductsRepository
):ViewModel() {

    private val _product = MutableStateFlow<List<Product>>(emptyList())
    val product = _product.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productsRepository.getProductsList().collectLatest { result->
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

    suspend fun postProductData(product: Product) {
        viewModelScope.launch {
            try {
                val response = productsRepository.postProductData(product) // Gọi hàm suspend

                if (response.isSuccessful) { // Kiểm tra xem yêu cầu thành công hay không
                    val responseBody = response.body() // Lấy body của response như thông thường
                    // Xử lý dữ liệu phản hồi tại đây
                } else {
                    // Xử lý lỗi từ phía server như trả về mã lỗi HTTP không mong đợi
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ, như lỗi mạng hoặc lỗi format dữ liệu
            }
        }
    }

}