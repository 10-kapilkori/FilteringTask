package com.task.filteringtask.retrofit

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun retrieveProductList() = apiInterface.retrieveProductList()
}