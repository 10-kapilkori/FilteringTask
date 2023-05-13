package com.task.filteringtask.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.filteringtask.retrofit.ApiHelper
import com.task.filteringtask.viewmodel.ProductViewModel

class ProductModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}