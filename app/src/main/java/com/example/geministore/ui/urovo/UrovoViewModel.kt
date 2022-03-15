package com.example.geministore.ui.urovo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.MainActivity
import com.example.geministore.R
import com.example.geministore.sharedPref

class UrovoViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val contextApp = application.applicationContext

    private val _bluetoothDeviceList = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }
    val bluetoothDeviceList: MutableLiveData<MutableList<String>> = _bluetoothDeviceList

    private val _urovoBluetoothAddress = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    private val _deviceId = MutableLiveData<String>().apply { value = "" }
    val deviceId: LiveData<String> = _deviceId

    private val _devicePos = MutableLiveData<Int>().apply { value = 0 }
    val devicePos: LiveData<Int> = _devicePos

    var bluetoothName: MutableList<String> = mutableListOf()
    var bluetoothAddress: MutableList<String> = mutableListOf()

    @SuppressLint("MissingPermission")
    fun getBluetoothDeviceAdapter(activity: Activity) {
        val bluetoothManager =
            activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothManager?.let { itBluetoothManager ->
            val bluetoothAdapter = itBluetoothManager.adapter
            bluetoothAdapter?.let { itAdapter ->
                itAdapter.bondedDevices.toCollection(mutableListOf<BluetoothDevice>())
                    .also { itDevices ->
                        itDevices.forEach {
                            bluetoothName.add(it.name)
                            bluetoothAddress.add(it.address)
                        }
                        _bluetoothDeviceList.value = bluetoothName
                        _urovoBluetoothAddress.value = bluetoothAddress
                    }
            }
        }
    }

    fun saveToSharedPreference(pos: Int) {
        val deviceID = bluetoothAddress[pos]
        val deviceName = bluetoothName[pos]
        sharedPref(contextApp).saveToSharedPreference(deviceID,deviceName)
        _deviceId.value = deviceID
    }

    fun getFromSharedPref(){
        _deviceId.value = sharedPref(contextApp).getDeviceID()
        _devicePos.value = bluetoothName.indexOf(sharedPref(contextApp).getDeviceNAme())
    }



    fun bluetoothPermissionWasGranted(context: Context) =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH
        ) == PackageManager.PERMISSION_GRANTED


    fun askForBluetoothPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.BLUETOOTH),
            1001
        )
    }

}