package com.example.geministore.services.retrofit


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {

    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET("hs/BitrixDelivery/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?): RetrofitDataModelOrder

    @Streaming
    @GET("hs/BitrixDelivery/getImage/")
    suspend fun getImageAsync(@Query("code") code:String?): Call<ResponseBody>

    @POST("hs/BitrixDelivery/SaveOrder")
    @Headers("Content-Type: application/json")
    suspend fun saveOrder(@Body requestBody: RetrofitDataModelOrder?): Response<RetrofitDataModelOrder?>?

}