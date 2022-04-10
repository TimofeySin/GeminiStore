package com.example.geministore.services.retrofit


import android.util.Log
import com.example.geministore.services.roomSqlManager.UploadManager
import com.example.geministore.services.roomSqlManager.UploadManagerDAO
import com.example.geministore.services.roomSqlManager.UploadManagerDatabase
import com.example.geministore.ui.order.orderModels.DataModelOrder
import com.example.geministore.ui.order.orderModels.DataModelOrderGoods
import com.example.geministore.ui.orderList.DataModelOrderList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.Request
import retrofit2.HttpException
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

    suspend fun getOrderAsync(idOrder: String?): DataModelOrder? {
        var dataModelOrder = DataModelOrder()

        coroutineScope{
            try {
                val retrofitDataModelOrder = apiService.getOrderAsync(idOrder)
                dataModelOrder = DataModelOrder(retrofitDataModelOrder)
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

    suspend fun saveDataOrder(dataModelOrderGlobal :DataModelOrder) {
        val retrofitDataModelOrderGlobal = dataModelOrderGlobal.getRetrofitDataModelOrder()
        coroutineScope {
            try {
                val uploadManager = UploadManager().getClientOrderUploadManager(retrofitDataModelOrderGlobal)



                val res = apiService.saveOrder(retrofitDataModelOrderGlobal)
                Log.d("crashsaveData", res.toString())
            } catch (notUseFullException: Exception) {
                Log.d("crashsaveData",
                    notUseFullException.printStackTrace().toString())   // or whatever logging
            }
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