package com.example.geministore.ui.order

import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods

class DataModelOrderGoods(orderGoods: RetrofitDataModelOrderGoods) {
     var priceGoods: Float = 0.0F
     var totalGoods: Float = 0.0F
     var completeGoods: Float = 0.0F
     var nameGoods: String = ""
     var commentGoods: String = ""
     var id: Int = 0
     var codes: MutableList<DataModelCodes> = mutableListOf()

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

