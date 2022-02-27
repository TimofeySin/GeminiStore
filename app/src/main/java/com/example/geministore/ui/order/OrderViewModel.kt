package com.example.geministore.ui.order

import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.services.retrofit.Common
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrderViewModel : ViewModel() {


    private val _commentClient = MutableLiveData<String>().apply {
        value = ""
    }
    val commentClient: LiveData<String> = _commentClient

    private val _commentOrder = MutableLiveData<String>().apply {
        value = ""
    }
    val commentOrder: LiveData<String> = _commentOrder

    private val _idOrder = MutableLiveData<String>().apply {
        value = ""
    }
    val idOrder: LiveData<String> = _idOrder

    private val _orderGoods = MutableLiveData<MutableList<DataModelOrderGoods>>().apply {
        value = mutableListOf()
    }
    val orderGoods: LiveData<MutableList<DataModelOrderGoods>> = _orderGoods


    fun fetchData(idOrder:String?)  {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val retrofitDataModelOrder = apiService.getOrderAsync(idOrder)
                val dataModelOrder = DataModelOrder(retrofitDataModelOrder)

                _commentClient.value = dataModelOrder.commentClient
                _commentOrder.value = dataModelOrder.commentOrder
                _idOrder.value = dataModelOrder.idOrder
                _orderGoods.value = dataModelOrder.orderGoods

              //  val call: Call<ResponseBody> = RetrofitClient.getClient.downloadFileUsingUrl("/images/pages/pic_w/6408.jpg")

            } catch  (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
                Log.d("crash",useFullException.printStackTrace().toString())   // or whatever logging
            }
        }
    }

    private fun wrapToBeTraceable(throwable: Throwable): Throwable {
        if (throwable is HttpException) {
            return Exception("${throwable.response()}", throwable)
        }
        return throwable
    }

    var code: String = ""

fun responseCode(code :String){

}




    private fun findPositionCode(Code: String): Int {



//        goods.forEach { itGood ->
//            itGood.getCodes().forEach { itCode ->
//                if (itCode.getCode() == Code) {
//                    //itGood.qu
//                    return itGood.getId()
//                }
//            }
//        }
        return 0
    }






}





