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




    fun fetchData(idOrder:String?,dateOrder:String?)  {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val response = apiService.getOrderAsync(idOrder,dateOrder)
                _orderGoods.value = response.getGoods()

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





