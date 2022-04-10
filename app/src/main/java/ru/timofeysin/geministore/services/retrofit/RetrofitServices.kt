package ru.timofeysin.geministore.services.retrofit

import retrofit2.Response
import retrofit2.http.*


interface RetrofitServices {

    @GET(Common.REST_URL + "/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET(Common.REST_URL + "/findGoods/")
    suspend fun getGoodsAsync(@Query("code") idGoods:String?): RetrofitDataModelOrderGoods

    @GET(Common.REST_URL + "/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?): RetrofitDataModelOrder

    @POST(Common.REST_URL + "/Order/")
    @Headers("Content-Type: application/json")
    suspend fun saveOrder(@Body requestBody: RetrofitDataModelOrder?): Response<RetrofitDataModelOrder?>?

}