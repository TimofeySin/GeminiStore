package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrder {

    @SerializedName("deliveryTime")
    @Expose
    private val deliveryTime: String = ""

    fun getDeliveryTime(): String {
        return deliveryTime
    }

    @SerializedName("manger")
    @Expose
    private val manger: String = ""

    fun getManger(): String {
        return manger
    }
    @SerializedName("date")
    @Expose
    private val date: String = ""

    fun getDate(): String {
        return date
    }
    @SerializedName("idOrder")
    @Expose
    private val idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }


    @SerializedName("goods")
    @Expose
    private val goods: Array<RetrofitDataModelOrderGoods> = arrayOf(RetrofitDataModelOrderGoods())

    fun getGoods():  Array<RetrofitDataModelOrderGoods> {
        return goods
    }




}