package com.example.geministore.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.data.retrofit.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OrderViewModel : ViewModel() {

    private val _orderGoods = MutableLiveData<Array<DataModelOrderGoods>>().apply {
        value = arrayOf(DataModelOrderGoods())
    }
    val orderGoods: LiveData<Array<DataModelOrderGoods>> = _orderGoods


    private val _deliveryTime = MutableLiveData<String>().apply {
        value = ""
    }
    val deliveryTime: LiveData<String> = _deliveryTime
    private val _manger = MutableLiveData<String>().apply {
        value = ""
    }
    val manger: LiveData<String> = _manger
    private val _date = MutableLiveData<String>().apply {
        value = ""
    }
    val date: LiveData<String> = _date
    private val _idOrder = MutableLiveData<String>().apply {
        value = ""
    }
    val idOrder: LiveData<String> = _idOrder


    fun fetchData(idOrder:String?,dateOrder:String?)  {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val response = apiService.getOrderAsync(idOrder,dateOrder)
                _orderGoods.value = response.getGoods()
                _deliveryTime.value = response.getDeliveryTime()
                _manger.value = response.getManger()
                _date.value = response.getDate()
                _idOrder.value = response.getIdOrder()


            } catch  (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
            Log.d("crash",useFullException.localizedMessage)
            // useFullException.printStackTrace()  // or whatever logging
            }
        }
    }
    private fun wrapToBeTraceable(throwable: Throwable): Throwable {
        if (throwable is HttpException) {
            return Exception("${throwable.response()}", throwable)
        }
        return throwable
    }


}





