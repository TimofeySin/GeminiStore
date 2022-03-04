package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrderList {

    @SerializedName("idOrder")
    @Expose
    private val idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }

    @SerializedName("Comment")
    @Expose
    private val comment: String = ""

    fun getComment(): String {
        return comment
    }

    @SerializedName("ClientName")
    @Expose
    private val clientName: String = ""

    fun getClientName(): String {
        return clientName
    }

    @SerializedName("deliveryTime")
    @Expose
    private val deliveryTime: String = ""

    fun getDeliveryTime(): String {
        return deliveryTime
    }

    @SerializedName("mobile")
    @Expose
    private val mobile: String = ""

    fun getMobile(): String {
        return mobile
    }

    @SerializedName("QuantityFull")
    @Expose
    private val quantityFull: Float = 0.0F

    fun getQuantityFull(): Float {
        return quantityFull
    }
    @SerializedName("QuantityComplete")
    @Expose
    private val quantityComplete: Float = 0.0F

    fun getQuantityComplete(): Float {
        return quantityComplete
    }

}