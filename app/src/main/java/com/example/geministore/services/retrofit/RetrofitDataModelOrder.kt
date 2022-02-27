package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrder {

    @SerializedName("comment_client")
    @Expose
    private val commentClient: String = ""

    fun getCommentClient(): String {
        return commentClient
    }

    @SerializedName("comment_order")
    @Expose
    private val commentOrder: String = ""

    fun getCommentOrder(): String {
        return commentOrder
    }

    @SerializedName("idOrder")
    @Expose
    private val idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }


    @SerializedName("goods")
    @Expose
    private val goods: Array<RetrofitDataModelOrderGoods> = arrayOf(RetrofitDataModelOrderGoods())

    fun getGoods():  Array<RetrofitDataModelOrderGoods> {
        return goods
    }
}