package ru.timofeysin.geministore.ui.login


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import ru.timofeysin.geministore.services.retrofit.Autherificator
import ru.timofeysin.geministore.services.retrofit.TakeInternetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {

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
        _resultCheck.postValue(msg)
    }
}