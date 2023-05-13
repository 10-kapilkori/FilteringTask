package com.task.filteringtask.retrofit

import com.task.filteringtask.pojos.ProductPojo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @Headers("Authorization: 5fd375cf3aa3e5979d90698280cf9104")
    @POST("v1/api/AllProdcutList")
    suspend fun retrieveProductList(
        @Field("Product_type") productType: String = "",
        @Field("Category") category: String = "",
        @Field("MinPrice") minPrice: String = "",
        @Field("MaxPrice") maxPrice: String = "",
        @Field("Subcategory") subcategory: String = "",
        @Field("SearchKeyWord") searchKeyWord: String = "",
        @Field("Langitude") latitude: String = "",
        @Field("Longitude") longitude: String = "",
        @Field("Distance") distance: String = "",
        @Field("Sort") sort: String = "",
        @Field("PageId") pageId: String = "",
        @Field("PerPage") perPage: Byte = 50,
        @Field("UserId") userId: Byte = 13,
        @Field("SaveProduct") saveProduct: String = "",
        @Field("Rating") rating: String = "",
    ): ProductPojo
}