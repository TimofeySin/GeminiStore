package com.example.geministore.ui.order


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.R
import com.example.geministore.services.retrofit.Common
import com.example.geministore.services.retrofit.RetrofitDataModelOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val contextApp = application.applicationContext
    private var dataModelOrder: DataModelOrder? = null
    private var retrofitDataModelOrderGlobal: RetrofitDataModelOrder? = null
    private val weightBarcode = "11"
    private var newModelOrderGoods: DataModelOrderGoods? = null
    private val ALERT_ADD = 1
    private val ALERT_ADD_NEW = 2
    private val ALERT_ERRORE = 3
    private val ALERT_NOT_FOUND = 4
    private val percentWeight = 1.15

    private val _commentClient = MutableLiveData<String>().apply { value = "" }
    val commentClient: LiveData<String> = _commentClient

    private val _commentOrder = MutableLiveData<String>().apply { value = "" }
    val commentOrder: LiveData<String> = _commentOrder

    private val _idOrder = MutableLiveData<String>().apply { value = "" }
    val idOrder: LiveData<String> = _idOrder

    private val _orderGoods =
        MutableLiveData<MutableList<DataModelOrderGoods>>().apply { value = mutableListOf() }
    val orderGoods: LiveData<MutableList<DataModelOrderGoods>> = _orderGoods

    private val _alertModel = MutableLiveData<AlertModel>().apply {
        value =
            AlertModel(4, 4, "", "", "", 0)
    }
    val alertModel: LiveData<AlertModel> = _alertModel

    private val _updaterAdapter = MutableLiveData<UpdaterAdapter>().apply { value = UpdaterAdapter(0,0) }
    val updaterAdapter: LiveData<UpdaterAdapter> = _updaterAdapter

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
        dataModelOrder?.let { itDataModel ->
            itDataModel.orderGoods.forEachIndexed { index,itGood ->
                itGood.codes.forEach { itCode ->
                    if (itCode.code == codeLocal) {
                        if (itWeight) {
                            if (itGood.completeGoods >= itGood.totalGoods * percentWeight) {
                                openAlert(ALERT_ERRORE,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            } else {
                                itGood.completeGoods += weight.toFloat()
                                openAlert(ALERT_ADD,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            }
                        } else {
                            if (itGood.completeGoods >= itGood.totalGoods) {
                                openAlert(ALERT_ERRORE,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            } else {
                                itGood.completeGoods++
                                openAlert(ALERT_ADD,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            }
                        }

                        _updaterAdapter.value = UpdaterAdapter(1,index)

                        return
                    }
                }
            }
            fetchGoodsData(codeLocal)
            newModelOrderGoods?.let {
                openAlert(ALERT_ADD_NEW, 0F, 0F, it.nameGoods)
                return
            }
            openAlert(ALERT_NOT_FOUND, 0F, 0F, codeLocal)
        }
    }

    private fun openAlert(alertCode: Int, complete: Float, total: Float, desc: String) {

        when (alertCode) {
            ALERT_ADD -> {
                val del = contextApp.getText(R.string.alert_delim).toString()
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = "",
                    totalAlert = "$complete $del $total",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.green))
            }
            ALERT_ADD_NEW -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.VISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_add_new).toString(),
                    totalAlert = "",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.red))
            }
            ALERT_ERRORE -> {
                val del = contextApp.getText(R.string.alert_delim).toString()
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_error).toString(),
                    totalAlert = "$complete $del $total",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.orange))
            }
            ALERT_NOT_FOUND -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_not_found).toString(),
                    totalAlert = "",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.red))
            }
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

    private fun fetchGoodsData(code: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiService = Common.makeRetrofitService
                val retrofitDataModelGoods = apiService.getGoodsAsync(code)
                newModelOrderGoods = DataModelOrderGoods(retrofitDataModelGoods)
            } catch (notUseFullException: Exception) {
                val useFullException = wrapToBeTraceable(notUseFullException)
                Log.d("crash",
                    useFullException.printStackTrace().toString())   // or whatever logging
            }
        }
    }

    fun addNewGoods() {
        newModelOrderGoods?.let { itDataNewGoods->
            dataModelOrder?.let {
                val size = it.orderGoods.size
                it.orderGoods.add(itDataNewGoods)
                _updaterAdapter.value = UpdaterAdapter(2,size)
            }
        }
    }
}





