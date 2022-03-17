package com.example.geministore.services.glide

import android.content.Context
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaderFactory
import com.bumptech.glide.load.model.LazyHeaders
import com.example.geministore.services.retrofit.Autherificator
import com.example.geministore.services.retrofit.Common


class ServiceGlide {
    private val server = Common.BASE_URL
    private val url = Common.REST_URL + "/getImage"



    fun getImage(code: String, imageView: ImageView, context: Context) {
        val glideUrl = GlideUrl(getUrl(code),getAuth())

        Glide.with(context).load(glideUrl).override(800, 800).into(imageView)
    }

    private fun getUrl(code: String): String {
        return "$server$url?code=$code"
    }

    private fun getAuth() :LazyHeaders{
        return LazyHeaders.Builder()
            .addHeader("Authorization", Autherificator.credential)
            .build()
    }


    class BasicAuthorization(private val username: String, private val password: String) :
        LazyHeaderFactory {
        override fun buildHeader(): String {
            return "Basic " + Base64.encodeToString("$username:$password".toByteArray(),
                Base64.NO_WRAP)
        }
    }


}