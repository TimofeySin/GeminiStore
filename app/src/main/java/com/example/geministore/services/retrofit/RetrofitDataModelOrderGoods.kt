package com.example.geministore.services.retrofit

import com.example.geministore.ui.order.DataModelOrderGoods
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

    fun RetrofitDataModelOrderGoods.toDataModelOrderGoods() = DataModelOrderGoods(
        nameGoods = nameGoods,
        weight = weight,
        id = id,
        codes = codes
    )




}