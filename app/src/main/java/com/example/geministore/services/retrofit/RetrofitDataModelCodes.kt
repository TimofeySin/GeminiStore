package com.example.geministore.services.retrofit


import com.example.geministore.ui.order.DataModelCodes
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelCodes{
    @SerializedName("code")
    @Expose
    private val code: String = ""

    fun getCode(): String {
        return code
    }

    fun RetrofitDataModelCodes.toDataModelCodes() = DataModelCodes(
        code = code
    )
}