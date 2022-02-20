package com.example.geministore.data.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrderGoods  {

    @SerializedName("namegoods")
    @Expose
    private val nameGoods: String = ""

    fun getNameGoods(): String {
        return nameGoods
    }

    @SerializedName("weight")
    @Expose
    private val weight: String = ""

    fun getWeight(): String {
        return weight
    }


    @SerializedName("id")
    @Expose
    private val id: Int = 0

    fun getId(): Int {
        return id
    }


    @SerializedName("codes")
    @Expose
    private val codes: Array<RetrofitDataModelCodes> = arrayOf(RetrofitDataModelCodes())

    fun getCodes():  Array<RetrofitDataModelCodes> {
        return codes
    }




}