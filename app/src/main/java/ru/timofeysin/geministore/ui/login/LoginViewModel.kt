package ru.timofeysin.geministore.ui.login


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.timofeysin.geministore.services.retrofit.Autherificator
import ru.timofeysin.geministore.services.retrofit.TakeInternetData
import ru.timofeysin.geministore.services.uploadWorker.UploadWorker
import java.util.concurrent.TimeUnit


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private var _resultCheck = MutableLiveData<String>().apply { value = "" }
    var resultCheck: LiveData<String> = _resultCheck

    fun checkLogin(login: String, password: String) {
        Autherificator.setCredentials(login, password)
        CoroutineScope(Dispatchers.IO).launch {
            TakeInternetData().checkLogin { result -> setResult(result) }
        }
    }

    private fun setResult(code: String) {
        var msg = ""
        when (code) {
            "200" -> msg = "OK"
            "404" -> msg = "Сервер не доступен"
            "500" -> msg = "Ошибка Сервера"
            "401" -> msg = "Ошибка авторизации"
            code -> msg = code
        }
        if(msg=="OK"){
            val myWorkRequest = PeriodicWorkRequest.Builder(UploadWorker::class.java,15,TimeUnit.MINUTES).build()
            WorkManager.getInstance().enqueue(myWorkRequest)
            Log.d("DAOManager","WorkManager.getInstance()")
        }
        _resultCheck.postValue(msg)
    }
}