package com.example.geministore.data.retrofit

import com.example.geministore.ui.order.DataModelOrder
import com.example.geministore.ui.orderList.DataModelOrderList
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("/hs/BitrixDelivery/OrderList")
    suspend fun getOrderListAsync(): Array<DataModelOrderList>

    @GET("hs/BitrixDelivery/Order/")
    suspend fun getOrderAsync(@Query("idOrder") idOrder:String?,@Query("dateOrder") dateOrder:String?): DataModelOrder



}