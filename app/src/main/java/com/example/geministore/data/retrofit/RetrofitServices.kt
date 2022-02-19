package com.example.geministore.data.retrofit

import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<DataModelOrderList>
}