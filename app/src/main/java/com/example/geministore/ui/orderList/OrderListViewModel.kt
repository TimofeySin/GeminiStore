package com.example.geministore.ui.orderList

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.data.retrofit.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException



class OrderListViewModel : ViewModel() {


    private var _orderList = MutableLiveData<Array<DataModelOrderList>>().apply {
        value = arrayOf(DataModelOrderList())
    }
    val orderList: LiveData<Array<DataModelOrderList>> = _orderList

    private var _refreshStatus = MutableLiveData<Boolean>().apply {
        value = false
    }
    val refreshStatus: LiveData<Boolean> = _refreshStatus


    fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val response = apiService.getOrderListAsync()
                _orderList.value = response
                _refreshStatus.value = false
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

}

