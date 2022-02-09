package com.example.geministore.data.retrofit



object Common {
    private const val BASE_URL = "http://192.168.32.9"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}