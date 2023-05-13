package com.task.filteringtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.task.filteringtask.retrofit.ApiHelper
import com.task.filteringtask.retrofit.Resource
import kotlinx.coroutines.Dispatchers

class ProductViewModel(private val apiHelper: ApiHelper) : ViewModel() {

    fun retrieveProductData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.retrieveProductList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}

