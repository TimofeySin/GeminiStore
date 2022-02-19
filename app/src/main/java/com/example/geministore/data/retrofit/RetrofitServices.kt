package com.example.geministore.data.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<DataModelOrderList>
}