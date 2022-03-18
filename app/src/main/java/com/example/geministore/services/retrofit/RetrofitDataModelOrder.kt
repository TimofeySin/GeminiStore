package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrder {

    @SerializedName("comment_client")
    @Expose
    private var commentClient: String = ""

    fun getCommentClient(): String {
        return commentClient
    }

    fun setCommentClient(value :String) {
        commentClient = value
    }

    @SerializedName("comment_order")
    @Expose
    private var commentOrder: String = ""

    fun getCommentOrder(): String {
        return commentOrder
    }

    fun setCommentOrder(value :String) {
        commentOrder = value
    }

    @SerializedName("idOrder")
    @Expose
    private var idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }

    fun setIdOrder(value :String) {
        idOrder = value
    }

    @SerializedName("goods")
    @Expose
    private var goods: Array<RetrofitDataModelOrderGoods?> = arrayOf(RetrofitDataModelOrderGoods())

    fun getGoods():  Array<RetrofitDataModelOrderGoods?> {
        return goods
    }

    fun setGoods(value : Array<RetrofitDataModelOrderGoods?>)   {
        goods = value
    }


}