package com.example.geministore.ui.order


import android.annotation.SuppressLint
import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geministore.R
import com.example.geministore.services.retrofit.RetrofitDataModelOrder
import com.example.geministore.services.retrofit.TakeInternetData
import com.example.geministore.ui.order.orderModels.AlertModel
import com.example.geministore.ui.order.orderModels.DataModelOrder
import com.example.geministore.ui.order.orderModels.DataModelOrderGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val contextApp = application.applicationContext
    private var dataModelOrder: DataModelOrder? = null
    private var retrofitDataModelOrderGlobal: RetrofitDataModelOrder? = null
    private val weightBarcode = "11"
    private var newModelOrderGoods: DataModelOrderGoods? = null
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

    private val _updaterAdapter =
        MutableLiveData<UpdaterAdapter>().apply { value = UpdaterAdapter(0, 0) }
    val updaterAdapter: LiveData<UpdaterAdapter> = _updaterAdapter

    fun fetchData(idOrder: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            dataModelOrder = TakeInternetData().getOrderAsync(idOrder)
            dataModelOrder?.let {
                _commentClient.value = it.commentClient
                _commentOrder.value = it.commentOrder
                _idOrder.value = it.idOrder
                _orderGoods.value = it.orderGoods
            }
        }
    }

    fun responseCode(code: String) {
        var codeLocal = code
        var weight = 0F
        var itWeight = false
        if (code.substring(0, 2) == weightBarcode) {
            codeLocal = code.substring(2, 7)
            weight = code.substring(7, 12).toFloat() / 1000F
            itWeight = true
        }
        dataModelOrder?.let { itDataModel ->
            itDataModel.orderGoods.forEachIndexed { index, itGood ->
                itGood.codes.forEach { itCode ->
                    if (itCode.code == codeLocal) {
                        if (itWeight) {
                            if (itGood.completeGoods + weight >= itGood.totalGoods * percentWeight) {
                                openAlert(
                                    AlertFrame.ALERT_ERROR_WEIGHT,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            } else {
                                itGood.completeGoods += weight
                                openAlert(
                                    AlertFrame.ALERT_ADD,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            }
                        } else {
                            if (itGood.completeGoods >= itGood.totalGoods) {
                                openAlert(
                                    AlertFrame.ALERT_ERROR,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            } else {
                                itGood.completeGoods++
                                openAlert(
                                    AlertFrame.ALERT_ADD,
                                    itGood.completeGoods,
                                    itGood.totalGoods,
                                    itGood.nameGoods)
                            }
                        }

                        _updaterAdapter.value = UpdaterAdapter(1, index)

                        return
                    }
                }
            }
            fetchGoodsData(codeLocal)
            newModelOrderGoods?.let {
                openAlert(AlertFrame.ALERT_ADD_NEW, 0F, 0F, it.nameGoods)
                return
            }
            openAlert(AlertFrame.ALERT_NOT_FOUND, 0F, 0F, codeLocal)
        }
    }

    private fun openAlert(alertCode: AlertFrame, complete: Float, total: Float, desc: String) {
        val del = contextApp.getText(R.string.alert_delim).toString()
        when (alertCode) {
            AlertFrame.ALERT_ADD -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = "",
                    totalAlert = "$complete $del $total",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.green))
            }
            AlertFrame.ALERT_ADD_NEW -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_add_new).toString(),
                    totalAlert = "",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.red))
            }
            AlertFrame.ALERT_ERROR -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_error).toString(),
                    totalAlert = "$complete $del $total",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.orange))
            }
            AlertFrame.ALERT_NOT_FOUND -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.INVISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_not_found).toString(),
                    totalAlert = "",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.red))
            }
            AlertFrame.ALERT_ERROR_WEIGHT -> {
                _alertModel.value = AlertModel(
                    buttonVisible = View.VISIBLE,
                    alertVisible = View.VISIBLE,
                    headAlert = contextApp.getText(R.string.alert_error).toString(),
                    totalAlert = "$complete $del $total",
                    descAlert = desc,
                    colorAlert = contextApp.getColor(R.color.orange))
            }
        }
    }

    fun saveData() {
        retrofitDataModelOrderGlobal?.let {
            CoroutineScope(Dispatchers.Main).launch {
                TakeInternetData().saveDataOrder(it)
            }
        }
    }

    private fun fetchGoodsData(code: String) {
        CoroutineScope(Dispatchers.Main).launch {
            newModelOrderGoods = TakeInternetData().getGoodsAsync(code)

        }
    }

    fun addNewGoods() {
        newModelOrderGoods?.let { itDataNewGoods ->
            dataModelOrder?.let {
                val size = it.orderGoods.size
                it.orderGoods.add(itDataNewGoods)
                _updaterAdapter.value = UpdaterAdapter(2, size)
            }
        }
    }
}





