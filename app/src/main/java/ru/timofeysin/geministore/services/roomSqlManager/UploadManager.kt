package ru.timofeysin.geministore.services.roomSqlManager

import androidx.room.*
import ru.timofeysin.geministore.services.retrofit.order.RetrofitDataModelOrder
import com.google.gson.Gson


@Entity(tableName = "upload_manager_table",primaryKeys = ["order_number","order_type"])
data class UploadManager
    (
    @ColumnInfo(name = "json_string")
    var jsonString: String = "",

    @ColumnInfo(name = "complete")
    var complete: Boolean = false,

    @ColumnInfo(name = "order_number")
    var orderNumber: String = "",

    @ColumnInfo(name = "order_type")
    var orderType: OrderType = OrderType.Empty,
)

{
    fun getClientOrderUploadManager(obj: RetrofitDataModelOrder) : UploadManager{
        val uploadManager = UploadManager()
        uploadManager.complete = false
        uploadManager.jsonString = Gson().toJson(obj)
        uploadManager.orderNumber = obj.getIdOrder()
        uploadManager.orderType = OrderType.ClientOrder
        return uploadManager
    }

}

