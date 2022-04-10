package ru.timofeysin.geministore.ui.order


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import ru.timofeysin.geministore.R
import ru.timofeysin.geministore.services.retrofit.TakeInternetData
import ru.timofeysin.geministore.services.roomSqlManager.UploadManager
import ru.timofeysin.geministore.services.roomSqlManager.UploadManagerDatabase
import ru.timofeysin.geministore.ui.order.orderModels.AddGoods
import ru.timofeysin.geministore.ui.order.orderModels.AlertModel
import ru.timofeysin.geministore.ui.order.orderModels.DataModelOrder
import ru.timofeysin.geministore.ui.order.orderModels.DataModelOrderGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.timofeysin.geministore.services.uploadWorker.UploadWorker



class OrderViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val contextApp = application.applicationContext
    private var dataModelOrder: DataModelOrder? = null
    private var workProgress = false
    private var newGoods: AddGoods? = null
    private val db = UploadManagerDatabase.getInstance(contextApp)
    private val _progressBar = MutableLiveData<Int>().apply { value = View.INVISIBLE }
    var progressBar: LiveData<Int> = _progressBar

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

    fun fetchData(idOrder: String) {
        _progressBar.postValue(View.VISIBLE)
        CoroutineScope(Dispatchers.IO).launch {
            dataModelOrder = TakeInternetData().getOrderAsync(idOrder,contextApp)
            dataModelOrder?.let {
                _commentClient.postValue(it.commentClient)
                _commentOrder.postValue(it.commentOrder)
                _idOrder.postValue(it.idOrder)
                _orderGoods.postValue(it.orderGoods)
                _progressBar.postValue(View.INVISIBLE)
            }
        }
    }

    fun responseCode(code: String) {
        if (workProgress) return
        _progressBar.postValue(View.VISIBLE)
        CoroutineScope(Dispatchers.IO).launch {
            workProgress = true
            dataModelOrder?.let {
                newGoods = AddGoods()
                newGoods!!.initGoods(code, it)
                checkGoods()
                _progressBar.postValue(View.INVISIBLE)
                workProgress = false
            }
        }
    }


    private fun checkGoods() {
        newGoods?.let {
            if (it.acceptAdd && it.position != 0) {
                openAlert(
                    AlertFrame.ALERT_ADD, it
                )
            } else if (it.acceptAdd && it.position == 0) {
                openAlert(AlertFrame.ALERT_ADD_NEW, it)
            } else if (it.itWeight) {
                openAlert(
                    AlertFrame.ALERT_ERROR_WEIGHT, it
                )
            } else if (!it.acceptAdd && it.position == 0) {
                openAlert(
                    AlertFrame.ALERT_NOT_FOUND, it
                )
            } else {
                openAlert(
                    AlertFrame.ALERT_ERROR, it
                )
            }
            if (it.acceptAdd && it.position != 0) {
                addQuantityGoods()
            }
        }
    }


    private fun openAlert(alertCode: AlertFrame, goods: AddGoods) {
        val del = contextApp.getText(R.string.alert_delim).toString()
        when (alertCode) {
            AlertFrame.ALERT_ADD -> {
                _alertModel.postValue(
                    AlertModel(
                        buttonVisible = View.INVISIBLE,
                        alertVisible = View.VISIBLE,
                        headAlert = "",
                        totalAlert = "${Math.round((goods.goods!!.completeGoods + goods.quantity) *1000F)/1000F} $del ${goods.goods!!.totalGoods}",
                        descAlert = goods.goods!!.nameGoods,
                        colorAlert = contextApp.getColor(R.color.green)
                    )
                )
            }
            AlertFrame.ALERT_ADD_NEW -> {
                _alertModel.postValue(
                    AlertModel(
                        buttonVisible = View.VISIBLE,
                        alertVisible = View.VISIBLE,
                        headAlert = contextApp.getText(R.string.alert_add_new).toString(),
                        totalAlert = goods.goods!!.completeGoods.toString(),
                        descAlert = goods.goods!!.nameGoods,
                        colorAlert = contextApp.getColor(R.color.red)
                    )
                )
            }
            AlertFrame.ALERT_ERROR -> {
                _alertModel.postValue(
                    AlertModel(
                        buttonVisible = View.INVISIBLE,
                        alertVisible = View.VISIBLE,
                        headAlert = contextApp.getText(R.string.alert_error).toString(),
                        totalAlert = "${goods.goods!!.completeGoods} $del ${goods.goods!!.totalGoods}",
                        descAlert = goods.goods!!.nameGoods,
                        colorAlert = contextApp.getColor(R.color.orange)
                    )
                )
            }
            AlertFrame.ALERT_NOT_FOUND -> {
                _alertModel.postValue(
                    AlertModel(
                        buttonVisible = View.INVISIBLE,
                        alertVisible = View.VISIBLE,
                        headAlert = contextApp.getText(R.string.alert_not_found).toString(),
                        totalAlert = "",
                        descAlert = goods.codeLocal,
                        colorAlert = contextApp.getColor(R.color.red)
                    )
                )
            }
            AlertFrame.ALERT_ERROR_WEIGHT -> {
                _alertModel.postValue(
                    AlertModel(
                        buttonVisible = View.VISIBLE,
                        alertVisible = View.VISIBLE,
                        headAlert = contextApp.getText(R.string.alert_error).toString(),
                        totalAlert = "${goods.goods!!.completeGoods} $del ${goods.goods!!.totalGoods}",
                        descAlert = goods.goods!!.nameGoods,
                        colorAlert = contextApp.getColor(R.color.orange)
                    )
                )
            }
        }
    }

    fun saveData() {
        dataModelOrder?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val uploadM = UploadManager().getClientOrderUploadManager(it.getRetrofitDataModelOrder())
                db.uploadManagerDAO.insert(uploadM)

                val myWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
                WorkManager.getInstance().enqueue(myWorkRequest)
                Log.d("DAOManager","WorkManager один запуск")
            }
        }
    }

    fun updateAdapter(action :Int,pos :Int){
        _updaterAdapter.postValue(UpdaterAdapter(action, pos))
    }

    fun addQuantityGoods() {
        newGoods?.let {
            if (it.position != 0) {
                dataModelOrder!!.orderGoods[it.position].completeGoods +=it.quantity

                dataModelOrder!!.orderGoods[it.position].completeGoods =  Math.round(dataModelOrder!!.orderGoods[it.position].completeGoods * 1000.0F) / 1000.0F
                updateAdapter(1,it.position)
                _updaterAdapter.postValue(UpdaterAdapter(1, it.position))
            } else {
                val size = dataModelOrder!!.orderGoods.size
                dataModelOrder!!.orderGoods.add(it.goods!!)
                updateAdapter(2,size)

            }
        }
    }
}





