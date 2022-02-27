package com.example.geministore.services.retrofit



object Common {
    private const val BASE_URL = "http://192.168.2.81:3001"
    val makeRetrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
    val getOhttp  =RetrofitClient.okHttpClient

    fun getUrl():String{
        return BASE_URL
    }
}