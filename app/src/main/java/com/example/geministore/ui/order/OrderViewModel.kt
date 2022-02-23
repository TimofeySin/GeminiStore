package com.example.geministore.ui.order

import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.data.retrofit.Common
import com.example.geministore.data.retrofit.RetrofitDataModelOrder
import com.example.geministore.data.retrofit.RetrofitDataModelOrderGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrderViewModel : ViewModel() {

    private var startScan : Boolean = false
private  var dataModelOrder : DataModelOrder = DataModelOrder()
    fun setStartScan(_startScan : Boolean){
        startScan = _startScan
    }

    private val _orderGoods = MutableLiveData<Array<RetrofitDataModelOrderGoods>>().apply {
        value = arrayOf(RetrofitDataModelOrderGoods())
    }
    val orderGoodsRetrofit: LiveData<Array<RetrofitDataModelOrderGoods>> = _orderGoods


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
                val retrofitDataModelOrder = apiService.getOrderAsync(idOrder,dateOrder)


                _orderGoods.value = retrofitDataModelOrder.getGoods()
                _deliveryTime.value = retrofitDataModelOrder.getDeliveryTime()
                _manger.value = retrofitDataModelOrder.getManger()
                _date.value = retrofitDataModelOrder.getDate()
                _idOrder.value = retrofitDataModelOrder.getIdOrder()
            } catch  (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
            //Log.d("crash",useFullException.localizedMessage)
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

    var code: String = ""

     fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (startScan) {
            val char : Char? = event?.displayLabel
            code += char.toString()
            Log.d("codeChar", code)
            Log.d("codeChar", keyCode.toString())
            if (keyCode == 66 || code.length ==13) {
                Log.d("codeChar", "Last char")
               val idGood = findPositionCode(code)
                if (idGood ==0){ Log.d("codeChar", "Not Found")}

                code = ""
            }
        }
    return true
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





