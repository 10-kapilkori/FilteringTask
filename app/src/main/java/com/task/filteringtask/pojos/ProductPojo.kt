package com.task.filteringtask.pojos

import com.google.gson.annotations.SerializedName

data class ProductPojo(
    @SerializedName("message")
    var message: String,
    @SerializedName("success")
    var success: String,
    @SerializedName("data")
    var productDataList: ArrayList<ProductDataList>
)

data class ProductDataList(
    @SerializedName("ProductData")
    var productData: ArrayList<ProductData>
)

data class ProductData(
    @SerializedName("Id")
    var id: String? = null,
    @SerializedName("UserId")
    var userId: String? = null,
    @SerializedName("UserName")
    var userName: String? = null,
    @SerializedName("UserProfile")
    var userProfile: String? = null,
    @SerializedName("Address")
    var address: String? = null,
    @SerializedName("ContactNo")
    var contactNo: String? = null,
    @SerializedName("WebsiteUrl")
    var websiteUrl: String? = null,
    @SerializedName("Longitude")
    var longitude: String? = null,
    @SerializedName("Langitude")
    var latitude: String? = null,
    @SerializedName("CategoryName")
    var categoryName: String? = null,
    @SerializedName("SubCategoryName")
    var subCategoryName: String? = null,
    @SerializedName("Name")
    var name: String? = null,
    @SerializedName("Currency")
    var currency: String? = null,
    @SerializedName("MinPrice")
    var minPrice: String? = null,
    @SerializedName("MaxPrice")
    var maxPrice: String? = null,
    @SerializedName("DiscountPrice")
    var discountPrice: String? = null,
    @SerializedName("Weight")
    var weight: String? = null,
    @SerializedName("DeliveryCharge")
    var deliveryCharge: String? = null,
    @SerializedName("Description")
    var description: String? = null,
    @SerializedName("Condition")
    var condition: String? = null,
    @SerializedName("Images")
    var images: String? = null,
    @SerializedName("Negotiation")
    var negotiation: String? = null,
    @SerializedName("SoldStatus")
    var soldStatus: String? = null,
    @SerializedName("ProductType")
    var productType: String? = null,
    @SerializedName("UserSince")
    var userSince: String? = null,
    @SerializedName("CreatAt")
    var createdAt: String? = null,
    @SerializedName("ProductSave")
    var productSave: String? = null,
    @SerializedName("ProductReport")
    var productReport: String? = null,
    @SerializedName("AverageRating")
    var averageRating: String? = null,
    @SerializedName("TotalUser")
    var totalUser: String? = null
)