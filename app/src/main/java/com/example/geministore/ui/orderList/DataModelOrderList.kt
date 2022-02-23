package com.example.geministore.ui.orderList

import com.example.geministore.data.retrofit.RetrofitDataModelOrderList

class DataModelOrderList(retrofit : RetrofitDataModelOrderList) {
var clientName  = ""
    var idOrder  = ""
    var mobile  = ""
    var comment  = ""
    var deliveryTime  = ""
    var quantityFull  = 0
    var quantityComplete  = 0

    init {
        this.clientName = retrofit.getClientName()
        this.idOrder = retrofit.getIdOrder()
        this.mobile = retrofit.getMobile()
        this.comment = retrofit.getComment()
        this.deliveryTime = retrofit.getDeliveryTime()
        this.quantityFull = retrofit.getQuantityFull()
        this.quantityComplete = retrofit.getQuantityComplete()
    }



}