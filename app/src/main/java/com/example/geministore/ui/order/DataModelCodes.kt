package com.example.geministore.ui.order

import com.example.geministore.services.retrofit.RetrofitDataModelCodes

class DataModelCodes(code: RetrofitDataModelCodes) {
     var code: String = ""
     init{
          this.code = code.getCode()
     }
}