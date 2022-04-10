package com.example.geministore.services.retrofit

import okhttp3.*


object Common {
    const val BASE_URL = "http://192.168.2.81:3001"
    //const val BASE_URL = "http://192.168.32.9"
    //const val BASE_URL = "http://31.135.9.35"
    val makeRetrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)

    const val REST_URL = "/bliznetsy_tim_sin/hs/BitrixDelivery"

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