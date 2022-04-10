package ru.timofeysin.geministore.services.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()


        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(Common.okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }
}
