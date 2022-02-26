package com.example.geministore.ui.urovo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geministore.MainActivity
import com.example.geministore.R

class UrovoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    private val _bluetoothDeviceList = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf<String>()
    }
     val bluetoothDeviceList: MutableLiveData<MutableList<String>> = _bluetoothDeviceList

    private val _urovoBluetoothAddress = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf<String>()
    }
    val urovoBluetoothAddress: MutableLiveData<MutableList<String>> = _urovoBluetoothAddress

    var bluetoothName :MutableList<String> = mutableListOf()
    var bluetoothAddress :MutableList<String> = mutableListOf()



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