package com.example.geministore.services.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrderGoods  {

    @SerializedName("price_good")
    @Expose
    private val priceGoods: Float = 0.0F

    fun getPriceGoods(): Float {
        return priceGoods
    }
    @SerializedName("good_total")
    @Expose
    private val totalGoods: Float = 0.0F

    fun getTotalGoods(): Float {
        return totalGoods
    }
    @SerializedName("good_complete")
    @Expose
    private val completeGoods: Float = 0.0F

    fun getCompleteGoods(): Float {
        return completeGoods
    }


    @SerializedName("name_goods")
    @Expose
    private val nameGoods: String = ""

    fun getNameGoods(): String {
        return nameGoods
    }

    @SerializedName("comment_good")
    @Expose
    private val commentGood: String = ""

    fun getCommentGoods(): String {
        return commentGood
    }

    @SerializedName("id")
    @Expose
    private val id: String = ""

    fun getId(): String {
        return id
    }


    @SerializedName("codes")
    @Expose
    private val codes: Array<RetrofitDataModelCodes> = arrayOf(RetrofitDataModelCodes())

    fun getCodes():  Array<RetrofitDataModelCodes> {
        return codes
    }

}