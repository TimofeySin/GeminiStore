package com.example.geministore.data.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelCodes {
    @SerializedName("code")
    @Expose
    private val code: String = ""

    fun getCode(): String {
        return code
    }
}