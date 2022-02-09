package com.example.geministore.data.retrofit

class TestData {


    fun GetData(): Array<List<String>> {
        val test = listOf<String>("ЦБ0001",
            "01022021",
            "Свиблов Себеряк",
            "10-02-15",
            "000254",
            "Берингов")

        val orders: Array<List<String>> = arrayOf(test, test, test, test, test)

        return orders


    }


}
