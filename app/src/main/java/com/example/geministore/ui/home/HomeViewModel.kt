package com.example.geministore.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.data.retrofit.TestData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text

    private var _orderList = MutableLiveData<Array<List<String>>>().apply {
        value = arrayOf(listOf(""))
    }
    val orderList: LiveData<Array<List<String>>> = _orderList




    fun fetchData(){
        val coroutineScore = CoroutineScope(Dispatchers.IO)
        coroutineScore.launch {
            Log.d("Track", "Start")
            // val retrofit = Common.retrofitService
            // val response = retrofit.getOrderListAsync()
            val retrofit = TestData()
            val response = retrofit.GetData()
            launch(Dispatchers.Main) {

                _orderList.value = response
            }

            }
        }
    }
