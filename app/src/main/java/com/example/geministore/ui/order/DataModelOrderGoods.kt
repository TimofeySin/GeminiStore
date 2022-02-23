package com.example.geministore.ui.order

import com.example.geministore.data.retrofit.RetrofitDataModelCodes

class DataModelOrderGoods(
     nameGoods: String,
     weight: String,
     id: Int,
     codes: Array<RetrofitDataModelCodes>
) {
     private var nameGoods: String = ""
     private var weight: String = ""
     private var id: Int = 0
     private var codes: Array<DataModelCodes> = arrayOf(DataModelCodes(""))

     init {
          this.nameGoods =nameGoods
          this.weight =weight
          this.id =  id

//          this.codes
//          codes.forEach {
//            this.codes = it
//          }




     }

}

