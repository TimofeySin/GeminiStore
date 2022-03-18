package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelCodes{
    @SerializedName("code")
    @Expose
    private var code: String = ""

    fun getCode(): String {
        return code
    }

    fun setCode(value : String) {
         code = value
    }
}