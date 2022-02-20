package com.example.geministore.data.retrofit

import retrofit2.http.*

interface RetrofitServices {
    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET("hs/BitrixDelivery/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?,@Query("dateOrder") dateOrder:String?): RetrofitDataModelOrder



}