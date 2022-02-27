package com.example.geministore.ui.order

import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.services.retrofit.Common
import com.example.geministore.services.retrofit.RetrofitDataModelOrder
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrderViewModel : ViewModel() {
    private var dataModelOrder: DataModelOrder? = null
    private var retrofitDataModelOrderGlobal: RetrofitDataModelOrder? = null
    private val weightBarcode = "11"


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


    fun fetchData(idOrder: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val retrofitDataModelOrder = apiService.getOrderAsync(idOrder)
                retrofitDataModelOrderGlobal = retrofitDataModelOrder
                dataModelOrder = DataModelOrder(retrofitDataModelOrder)
                dataModelOrder?.let {
                    _commentClient.value = it.commentClient
                    _commentOrder.value = it.commentOrder
                    _idOrder.value = it.idOrder
                    _orderGoods.value = it.orderGoods
                }
            } catch (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
                Log.d("crash",
                    useFullException.printStackTrace().toString())   // or whatever logging
            }
        }
    }

    private fun wrapToBeTraceable(throwable: Throwable): Throwable {
        if (throwable is HttpException) {
            return Exception("${throwable.response()}", throwable)
        }
        return throwable
    }

    fun responseCode(code: String) {
        var codeLocal = code
        var weight = ""
        var itWeight = false
        if (code.substring(0, 1) == weightBarcode) {
            codeLocal = code.substring(2, 7)
            weight = code.substring(8, 11)
            itWeight = true
        }

        dataModelOrder?.let {
            it.orderGoods.forEach { itGood ->
                itGood.codes.forEach { itCode ->
                    if (itCode.code == codeLocal) {

                        if (itWeight) {
                            itGood.completeGoods += weight.toFloat()
                        } else {
                            itGood.completeGoods++
                        }
                    }
                }
            }
            _orderGoods.value = it.orderGoods
        }
    }

    fun saveData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val res = apiService.saveOrder(retrofitDataModelOrderGlobal)
                Log.d("crash", res.toString())
            } catch (notUseFullException: Exception) {
                Log.d("crash",
                    notUseFullException.printStackTrace().toString())   // or whatever logging
            }

        }
    }


}





