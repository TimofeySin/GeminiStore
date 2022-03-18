package com.example.geministore.ui.order.orderModels

import com.example.geministore.services.retrofit.RetrofitDataModelOrder
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods


class DataModelOrder(modelOrder: RetrofitDataModelOrder) {
    var commentClient: String = ""
    var commentOrder: String = ""
    var idOrder: String = ""
    var orderGoods: MutableList<DataModelOrderGoods> = mutableListOf()

    constructor() : this(RetrofitDataModelOrder())


    init {
        this.commentClient = modelOrder.getCommentClient()
        this.commentOrder = modelOrder.getCommentOrder()
        this.idOrder = modelOrder.getIdOrder()
        modelOrder.getGoods().forEach {
            orderGoods.add(DataModelOrderGoods(it))
        }
    }

    fun getRetrofitDataModelOrder(): RetrofitDataModelOrder {
        val retrofitDataModelOrder = RetrofitDataModelOrder()
        retrofitDataModelOrder.setCommentClient(commentClient)
        retrofitDataModelOrder.setIdOrder(idOrder)
        retrofitDataModelOrder.setCommentOrder(commentOrder)

        val arrayGoods = arrayOfNulls<RetrofitDataModelOrderGoods>(orderGoods.size)
        for (i in arrayGoods.indices) {
            arrayGoods[i] = orderGoods[i].getRetrofitDataModelOrderGoods()
        }
        retrofitDataModelOrder.setGoods(arrayGoods)
        return retrofitDataModelOrder
    }

}