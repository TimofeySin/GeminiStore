package com.example.geministore.services.retrofit

import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {

    @GET("/bliznetsy_bitrix/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET("/bliznetsy_bitrix/hs/BitrixDelivery/findGoods/")
    suspend fun getGoodsAsync(@Query("idGoods") idGoods:String?): RetrofitDataModelOrderGoods

    @GET("/bliznetsy_bitrix/hs/BitrixDelivery/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?): RetrofitDataModelOrder

    @POST("/bliznetsy_bitrix/hs/BitrixDelivery/Order/")
    @Headers("Content-Type: application/json")
    suspend fun saveOrder(@Body requestBody: RetrofitDataModelOrder?): Response<RetrofitDataModelOrder?>?

}