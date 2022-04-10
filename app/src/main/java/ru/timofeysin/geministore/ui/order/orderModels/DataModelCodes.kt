package ru.timofeysin.geministore.ui.order.orderModels

import ru.timofeysin.geministore.services.retrofit.RetrofitDataModelCodes

class DataModelCodes(code: RetrofitDataModelCodes?) {
    var code: String = ""

    init {
        this.code = code!!.getCode()
    }

    fun getRetrofitDataModelCodes(): RetrofitDataModelCodes {
        val retrofitDataModelCodes = RetrofitDataModelCodes()
        retrofitDataModelCodes.setCode(code)
        return retrofitDataModelCodes
    }
}