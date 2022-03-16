package com.example.geministore.ui.orderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.services.retrofit.TakeInternetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderListViewModel : ViewModel() {


    private var _orderList = MutableLiveData<MutableList<DataModelOrderList>>().apply {
        value = mutableListOf()
    }
    val orderListRetrofit: LiveData<MutableList<DataModelOrderList>> = _orderList

    private var _refreshStatus = MutableLiveData<Boolean>().apply {
        value = false
    }
    val refreshStatus: LiveData<Boolean> = _refreshStatus


       fun fetchData() {
          CoroutineScope(Dispatchers.Main).launch {
              val listOfDataOrder = TakeInternetData().getOrderListAsync()
              _orderList.value = listOfDataOrder
              _refreshStatus.value =false
          }
    }
}

