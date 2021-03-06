package ru.timofeysin.geministore.ui.orderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.timofeysin.geministore.services.retrofit.TakeInternetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.timofeysin.geministore.models.orderListModel.DataModelOrderList


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
          CoroutineScope(Dispatchers.IO).launch {
              val listOfDataOrder = TakeInternetData().getOrderListAsync()
              _orderList.postValue(listOfDataOrder)
              _refreshStatus.postValue(false)
          }
    }
}

