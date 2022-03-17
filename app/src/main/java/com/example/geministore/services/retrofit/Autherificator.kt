package com.example.geministore.services.retrofit


import okhttp3.Credentials
import java.nio.charset.Charset

object Autherificator {

    var credential : String = ""

    fun setCredentials(login:String,pas:String){
        credential =  Credentials.basic(login, pas, Charset.forName("UTF-8"))
    }
}