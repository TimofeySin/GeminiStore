package com.example.geministore.services.retrofit

import okhttp3.*


object Common {
    // private const val BASE_URL = "http://192.168.2.81:3001"
    const val BASE_URL = "http://192.168.32.9"
    val makeRetrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)


    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .authenticator { _: Route?, response: Response ->
            val request: Request = response.request()
            if (request.header("Authorization") != null) // Логин и пароль неверны
                return@authenticator null
            request.newBuilder()
                .header("Authorization", Autherificator.credential)
                .build()
        }
        .build()


}