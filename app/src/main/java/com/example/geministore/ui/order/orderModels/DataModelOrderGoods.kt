package com.example.geministore.ui.order.orderModels

import com.example.geministore.services.retrofit.RetrofitDataModelCodes
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods

class DataModelOrderGoods(orderGoods: RetrofitDataModelOrderGoods?) {
     var priceGoods: Float = 0.0F
     var totalGoods: Float = 0.0F
     var completeGoods: Float = 0.0F
     var nameGoods: String = ""
     var commentGoods: String = ""
     var id: String = ""
     var codes: MutableList<DataModelCodes> = mutableListOf()

    constructor() : this(RetrofitDataModelOrderGoods())

    init {
        this.priceGoods = orderGoods!!.getPriceGoods()
        this.totalGoods = orderGoods.getTotalGoods()
        this.completeGoods = orderGoods.getCompleteGoods()
        this.nameGoods = orderGoods.getNameGoods()
        this.commentGoods = orderGoods.getCommentGoods()
        this.id = orderGoods.getId()
        orderGoods.getCodes().forEach {
            this.codes.add(DataModelCodes(it))
        }
    }


   fun getRetrofitDataModelOrderGoods() :RetrofitDataModelOrderGoods{
       val  retrofitDataModelOrderGoods = RetrofitDataModelOrderGoods()
       retrofitDataModelOrderGoods.setCommentGoods(commentGoods)
       retrofitDataModelOrderGoods.setCompleteGoods(completeGoods)
       retrofitDataModelOrderGoods.setId(id)
       retrofitDataModelOrderGoods.setNameGoods(nameGoods)
       retrofitDataModelOrderGoods.setPriceGoods(priceGoods)
       retrofitDataModelOrderGoods.setTotalGoods(totalGoods)

       val arrayCodes = arrayOfNulls<RetrofitDataModelCodes>(codes.size)
       for (i in arrayCodes.indices) {
           arrayCodes[i] = codes[i].getRetrofitDataModelCodes()
       }
       retrofitDataModelOrderGoods.setCodes(arrayCodes)

       return retrofitDataModelOrderGoods
   }
}

