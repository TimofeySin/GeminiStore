package ru.timofeysin.geministore.services.retrofit


import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ru.timofeysin.geministore.services.roomSqlManager.UploadManager
import ru.timofeysin.geministore.ui.order.orderModels.DataModelOrder
import ru.timofeysin.geministore.ui.order.orderModels.DataModelOrderGoods
import ru.timofeysin.geministore.ui.orderList.DataModelOrderList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Request
import retrofit2.HttpException
import ru.timofeysin.geministore.services.roomSqlManager.OrderType
import ru.timofeysin.geministore.services.roomSqlManager.UploadManagerDatabase
import java.io.IOException

class TakeInternetData {



    private val apiService = Common.makeRetrofitService
    suspend fun getOrderListAsync(): MutableList<DataModelOrderList> {
        val listOfDataOrder : MutableList<DataModelOrderList> = mutableListOf()
        coroutineScope {
            launch {
                try {
                    val response = apiService.getOrderListAsync()
                    response.forEach {
                        listOfDataOrder.add(DataModelOrderList(it))
                    }
                } catch  (notUseFullException: Exception) {
                    val useFullException = wrapToBeTraceable(notUseFullException)
                    Log.d("crash",useFullException.printStackTrace().toString())   // or whatever logging
                }
            }
        }
        return listOfDataOrder
    }

    suspend fun getOrderAsync(idOrder: String,context:Context): DataModelOrder {
        var dataModelOrder = DataModelOrder()
        val db = UploadManagerDatabase.getInstance(context)
        coroutineScope{
            try {
                val roomRes =db.uploadManagerDAO.getForOrderNumberAndType(idOrder,OrderType.ClientOrder)
                if(roomRes==null){
                val retrofitDataModelOrder = apiService.getOrderAsync(idOrder)
                dataModelOrder = DataModelOrder(retrofitDataModelOrder)
                }else{
                    val retrofitDataModelOrder =  Gson().fromJson(roomRes.jsonString,RetrofitDataModelOrder::class.java)
                    dataModelOrder = DataModelOrder(retrofitDataModelOrder)
                }
            } catch (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
                Log.d("crash",
                    useFullException.printStackTrace().toString())   // or whatever logging
            }
        }
        return dataModelOrder
    }

    suspend fun getGoodsAsync(code: String): DataModelOrderGoods {
       var newModelOrderGoods = DataModelOrderGoods()
        coroutineScope {
            try {
                val retrofitDataModelGoods = apiService.getGoodsAsync(code)
                newModelOrderGoods = DataModelOrderGoods(retrofitDataModelGoods)
            } catch (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
                Log.d("crash",
                    useFullException.printStackTrace().toString())   // or whatever logging
            }
        }

        return newModelOrderGoods
    }

    suspend fun saveDataOrder(context:Context) {
        val db = UploadManagerDatabase.getInstance(context)
        coroutineScope {
            try {
                var res = ""
                val listOFOrders : List<UploadManager>? = db.uploadManagerDAO.getAllForUpload()
                Log.d("DAOManager","getAllForUpload " + listOFOrders!!.size.toString() )
                    listOFOrders.forEach {
                        res = apiService.saveOrder(it.jsonString)
                        Log.d("DAOManager",res)
                        if (res == "OK"){
                            it.complete=true
                            db.uploadManagerDAO.insert(it)
                        }
                    }
                Log.d("crashSaveData", res)
            } catch (notUseFullException: Exception) {
                Log.d("crashSaveData",
                    notUseFullException.printStackTrace().toString())   // or whatever logging
            }
            db.uploadManagerDAO.deleteAllComplete()
        }
    }

    suspend fun checkLogin(myCallback: (result: String) -> Unit)  {
        coroutineScope {
            val deferredResult = async {
                val client = Common.okHttpClient
                val request = Request.Builder()
                    .url(Common.BASE_URL + Common.REST_URL + "/Check")
                    .build()
                try {
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            return@async response.code().toString()
                        }else{
                            return@async response.code().toString()
                        }
                    }
                } catch (e: IOException ) {
                }
                return@async ""
            }
            val result = deferredResult.await()

            myCallback.invoke(result)
        }
    }

    private fun wrapToBeTraceable(throwable: Throwable): Throwable {
        if (throwable is HttpException) {
            return Exception("${throwable.response()}", throwable)
        }
        return throwable
    }

}