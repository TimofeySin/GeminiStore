package com.example.geministore.ui.order.orderModels

import com.example.geministore.services.retrofit.RetrofitDataModelOrder


class DataModelOrder (modelOrder :RetrofitDataModelOrder) {
     var commentClient: String = ""
     var commentOrder: String = ""
     var idOrder: String = ""
     var orderGoods: MutableList<DataModelOrderGoods> = mutableListOf()

     constructor() : this(RetrofitDataModelOrder())



    init {
        this.commentClient = modelOrder.getCommentClient()
        this.commentOrder= modelOrder.getCommentOrder()
        this.idOrder= modelOrder.getIdOrder()
        modelOrder.getGoods().forEach {
            orderGoods.add(DataModelOrderGoods(it))
        }
    }
}