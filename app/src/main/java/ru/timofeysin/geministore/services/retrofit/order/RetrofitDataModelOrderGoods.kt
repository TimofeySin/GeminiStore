package ru.timofeysin.geministore.services.retrofit.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrderGoods  {

    @SerializedName("price_good")
    @Expose
    private var priceGoods: Float = 0.0F

    fun getPriceGoods(): Float {
        return priceGoods
    }

    fun setPriceGoods(value : Float) {
         priceGoods = value
    }

    @SerializedName("good_total")
    @Expose
    private var totalGoods: Float = 0.0F

    fun getTotalGoods(): Float {
        return totalGoods
    }

    fun setTotalGoods(value : Float) {
        totalGoods = value
    }

    @SerializedName("good_complete")
    @Expose
    private var completeGoods: Float = 0.0F

    fun getCompleteGoods(): Float {
        return completeGoods
    }

    fun setCompleteGoods(value : Float) {
        completeGoods = value
    }

    @SerializedName("name_goods")
    @Expose
    private var nameGoods: String = ""

    fun getNameGoods(): String {
        return nameGoods
    }

    fun setNameGoods(value : String) {
        nameGoods = value
    }

    @SerializedName("comment_good")
    @Expose
    private var commentGood: String = ""

    fun getCommentGoods(): String {
        return commentGood
    }

    fun setCommentGoods(value : String) {
        commentGood = value
    }

    @SerializedName("id")
    @Expose
    private var id: String = ""

    fun getId(): String {
        return id
    }

    fun setId(value : String) {
        id = value
    }

    @SerializedName("codes")
    @Expose
    private var codes: Array<RetrofitDataModelCodes?> = arrayOf(RetrofitDataModelCodes())

    fun getCodes():  Array<RetrofitDataModelCodes?> {
        return codes
    }

    fun setCodes(value :  Array<RetrofitDataModelCodes?>) {
        codes = value
    }

}