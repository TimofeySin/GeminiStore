package com.example.geministore.ui.orderList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.data.retrofit.Common
import com.example.geministore.data.retrofit.DataModelOrderList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

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
        val coroutineScore = CoroutineScope(Dispatchers.IO)
        coroutineScore.launch {
            val apiService = Common.makeRetrofitService
            CoroutineScope(Dispatchers.Main).launch {

                   try {
                       val response = apiService.getOrderListAsync()
                       _orderList.value  = response
                       _refreshStatus.value = false
                   }catch (e: IOException) {
                       Log.d("crash",e.toString())
                   }


                }
            }
        }
    }

