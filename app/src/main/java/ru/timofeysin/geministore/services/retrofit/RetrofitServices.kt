package ru.timofeysin.geministore.services.retrofit

import retrofit2.http.*
import ru.timofeysin.geministore.services.retrofit.order.RetrofitDataModelOrder
import ru.timofeysin.geministore.services.retrofit.order.RetrofitDataModelOrderGoods
import ru.timofeysin.geministore.services.retrofit.orderList.RetrofitDataModelOrderList


interface RetrofitServices {

    @GET(Common.REST_URL + "/OrderList")
    suspend fun getOrderListAsync(): Array<RetrofitDataModelOrderList>

    @GET(Common.REST_URL + "/findGoods/")
    suspend fun getGoodsAsync(@Query("code") idGoods:String?): RetrofitDataModelOrderGoods

    @GET(Common.REST_URL + "/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?): RetrofitDataModelOrder

    @POST(Common.REST_URL + "/SaveOrder/")
    @Headers("Content-Type: application/json")
    suspend fun saveOrder(@Body requestBody: String): String

}