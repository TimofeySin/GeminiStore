package ru.timofeysin.geministore.services.retrofit.orderMove
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetrofitDataModelOrderMoveList {

    @SerializedName("idOrder")
    @Expose
    private val idOrder: String = ""

    fun getIdOrder(): String {
        return idOrder
    }


    @SerializedName("Comment")
    @Expose
    private val comment: String = ""

    fun getComment(): String {
        return comment
    }

    @SerializedName("date")
    @Expose
    private val date: String = ""

    fun getDate(): String {
        return date
    }


    @SerializedName("from_warehouse")
    @Expose
    private val fromWarehouse: String = ""

    fun getFromWarehouse(): String {
        return fromWarehouse
    }

    @SerializedName("to_warehouse")
    @Expose
    private val toWarehouse: String = ""

    fun getToWarehouse(): String {
        return toWarehouse
    }

}