package com.example.geministore.data.retrofit

import com.example.geministore.data.OrderData
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface RetrofitServices {
    @GET("bliznetsy_tim_sin/hs/BitrixDelivery/OrderList")
    fun getOrderListAsync(): Deferred<MutableList<OrderData>>
}