package com.example.geministore.services.retrofit

import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {

    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET("hs/BitrixDelivery/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?): RetrofitDataModelOrder

    @GET("hs/BitrixDelivery/Goods")
    suspend fun getGoodsAsync(@Query("idGoods") idGoods:String?): RetrofitDataModelOrderGoods

    @POST("hs/BitrixDelivery/SaveOrder")
    @Headers("Content-Type: application/json")
    suspend fun saveOrder(@Body requestBody: RetrofitDataModelOrder?): Response<RetrofitDataModelOrder?>?

}