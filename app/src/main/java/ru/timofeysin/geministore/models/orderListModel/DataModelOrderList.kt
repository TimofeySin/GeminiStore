package ru.timofeysin.geministore.models.orderListModel

import ru.timofeysin.geministore.services.retrofit.orderList.RetrofitDataModelOrderList

class DataModelOrderList(retrofit : RetrofitDataModelOrderList) {
var clientName  = ""
    var idOrder  = ""
    var mobile  = ""
    var comment  = ""
    var deliveryTime  = ""
    var quantityFull  = 0.0F
    var quantityComplete  = 0.0F

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