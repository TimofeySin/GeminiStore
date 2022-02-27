package com.example.geministore.ui.order

import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods

class DataModelOrderGoods(orderGoods: RetrofitDataModelOrderGoods) {
    private var priceGoods: Float = 0.0F
    private var totalGoods: Float = 0.0F
    private var completeGoods: Float = 0.0F
    private var nameGoods: String = ""
    private var commentGoods: String = ""
    private var id: Int = 0
    private var codes: MutableList<DataModelCodes> = mutableListOf()

    init {
        this.priceGoods = orderGoods.getPriceGoods()
        this.totalGoods = orderGoods.getTotalGoods()
        this.completeGoods = orderGoods.getCompleteGoods()
        this.nameGoods = orderGoods.getNameGoods()
        this.commentGoods = orderGoods.getCommentGoods()
        this.id = orderGoods.getId()
        orderGoods.getCodes().forEach {
            this.codes.add(DataModelCodes(it))
        }
    }
}

