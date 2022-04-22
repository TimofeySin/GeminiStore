package ru.timofeysin.geministore.models.orderMoveList

import ru.timofeysin.geministore.services.retrofit.order.RetrofitDataModelCodes
import ru.timofeysin.geministore.services.retrofit.order.RetrofitDataModelOrderGoods
import ru.timofeysin.geministore.services.retrofit.orderMove.RetrofitDataModelOrderMoveList


class DataModelOrderMoveList (orderMoveList: RetrofitDataModelOrderMoveList) {
    var idOrder: String = ""
    var comment: String = ""
    var date: String = ""
    var fromWarehouse: String = ""
    var toWarehouse: String = ""

    constructor() : this(RetrofitDataModelOrderMoveList())

    init {
        this.idOrder = orderMoveList.getIdOrder()
        this.comment = orderMoveList.getComment()
        this.date = orderMoveList.getDate()
        this.fromWarehouse = orderMoveList.getFromWarehouse()
        this.toWarehouse = orderMoveList.getToWarehouse()

    }
}