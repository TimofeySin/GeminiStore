package com.example.geministore.services.retrofit

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .authenticator { _: Route?, response: Response ->
            val request: Request = response.request()
            if (request.header("Authorization") != null) // Логин и пароль неверны
                return@authenticator null
            request.newBuilder()
                .header("Authorization", Credentials.basic("Android", "123"))
                .build()
        }
        .build()



    fun getClient(baseUrl: String): Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
