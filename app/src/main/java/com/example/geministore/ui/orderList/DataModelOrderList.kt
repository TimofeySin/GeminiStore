package com.example.geministore.ui.orderList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataModelOrderList {

    @SerializedName("idOrder")
    @Expose
    private val idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }

    @SerializedName("date")
    @Expose
    private val date: String = ""

    fun getDate(): String {
        return date
    }

    @SerializedName("manger")
    @Expose
    private val manger: String = ""

    fun getManger(): String {
        return manger
    }

    @SerializedName("deliveryTime")
    @Expose
    private val deliveryTime: String = ""

    fun getDeliveryTime(): String {
        return deliveryTime
    }

    @SerializedName("number")
    @Expose
    private val number: String = ""

    fun getNumber(): String {
        return number
    }

    @SerializedName("magazin")
    @Expose
    private val magazin: String = ""

    fun getMagazin(): String {
        return magazin
    }


}