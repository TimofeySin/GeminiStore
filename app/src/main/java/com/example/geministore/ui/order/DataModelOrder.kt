package com.example.geministore.ui.order

import com.example.geministore.services.retrofit.RetrofitDataModelOrder
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods

class DataModelOrder(modelOrder :RetrofitDataModelOrder) {
    private var commentClient: String = ""
    private var commentOrder: String = ""
    private var date: String = ""
    private var idOrder: String = ""

    private var goods: MutableList<RetrofitDataModelOrderGoods> = mutableListOf()

    init {
        this.commentClient = modelOrder.getCommentClient()
        this.commentOrder= modelOrder.getCommentOrder()
        this.date= modelOrder.getDate()
        this.idOrder= modelOrder.getIdOrder()
        modelOrder.getGoods().forEach {
            goods.add(it)
        }
    }
}