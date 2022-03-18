package com.example.geministore.ui.order.orderModels

import com.example.geministore.services.retrofit.TakeInternetData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGoods {
    private val weightBarcode = "11"
    var itWeight = false
    var quantity = 0F
    var goods: DataModelOrderGoods? = null
    var position = 0
    var codeLocal = ""
    var acceptAdd = false
    private val percentWeight = 1.15

    suspend fun initGoods(code: String, order: DataModelOrder){

        if (code.substring(0, 2) == weightBarcode) {
            quantity = Math.round(code.substring(7, 12).toFloat()) / 1000F
            itWeight = true
            codeLocal = code.substring(2, 7)
        } else {
            quantity = 1F
            itWeight = false
            codeLocal = code

        }

        order.let { itDataModel ->
            itDataModel.orderGoods.forEachIndexed { index, itGood ->
                itGood.codes.forEach { itCode ->
                    if (codeLocal == itCode.code) {
                        position = index
                        goods = itGood
                        acceptAdd =
                            (!itWeight && itGood.totalGoods >= itGood.completeGoods + quantity) ||
                                    (itWeight && itGood.totalGoods * percentWeight >= itGood.completeGoods + quantity) ||
                                    (itGood.totalGoods == 0F)
                    }
                }
            }
        }
        if (position == 0) {
            fetchGoodsData(codeLocal)
        }


    }

      private suspend fun fetchGoodsData(code: String) {
         // dataModelOrder!!.orderGoods[it.position].completeGoods =  Math.round(dataModelOrder!!.orderGoods[it.position].completeGoods * 1000.0F) / 1000.0F
            goods = TakeInternetData().getGoodsAsync(code)
            goods!!.completeGoods =Math.round(quantity * 1000.0F) / 1000.0F
            acceptAdd = goods!!.id!=""


    }


}