package ru.timofeysin.geministore

import android.content.Context

class SharedPref(private val contextApp: Context) {
    private val nameShared = "gemini_store"
    private val deviceIDPref = "deviceID"
    private val deviceNamePref = "deviceName"

    fun saveToSharedPreference(deviceID: String,deviceName :String ) {
        val sharedPreference =  contextApp.getSharedPreferences(nameShared, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(deviceIDPref,deviceID)
        editor.putString(deviceNamePref,deviceName)
        editor.apply()
    }

    fun getDeviceID(): String? {
        val sharedPreference =  contextApp.getSharedPreferences(nameShared, Context.MODE_PRIVATE)
        return sharedPreference.getString(deviceIDPref,null)

    }

    fun getDeviceNAme(): String? {
        val sharedPreference =  contextApp.getSharedPreferences(nameShared, Context.MODE_PRIVATE)
        return sharedPreference.getString(deviceNamePref,"")
    }



}