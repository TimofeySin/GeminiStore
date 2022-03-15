package com.example.geministore.services.urovo

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.withStyledAttributes
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.geministore.MainActivity
import com.example.geministore.sharedPref
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.lang.Thread.sleep
import java.nio.charset.StandardCharsets
import java.util.*


class UrovoConnectionService : Service() {

    private val idBluetoothSocket = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val CHANNEL_ID = "UrovoConnectionServiceChannel"
    private val NOTIFICATION_ID = 1
    private val readBuffer = ByteArray(128)

    override fun onBind(p0: Intent?): IBinder {
        Log.d("BlueT", "onBind");
        createNotification()
        getCoroutineScopeInputStream()
        return Binder()
    }

    private fun getCoroutineScopeInputStream() {
        CoroutineScope(Dispatchers.IO).launch {
            getAsyncInputStream()
        }
    }

    private fun getBluetoothAdapter(): BluetoothAdapter? {
        var bluetoothAdapter: BluetoothAdapter? = null
        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothManager?.let { itBluetoothManager ->
            bluetoothAdapter = itBluetoothManager.adapter
            bluetoothAdapter?.let { itAdapter -> bluetoothAdapter = itAdapter }
        }
        return bluetoothAdapter
    }

    private fun getBluetoothDevice(bluetoothAdapter: BluetoothAdapter): BluetoothDevice? {
        val deviceAddress = sharedPref(applicationContext).getDeviceID()
        return bluetoothAdapter.getRemoteDevice(deviceAddress)
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceSocket(device: BluetoothDevice): BluetoothSocket? {
        var socket: BluetoothSocket? = null
        try {
            socket = device.createRfcommSocketToServiceRecord(idBluetoothSocket)
            socket.connect()
        } catch (e: IOException) {
            socket?.close()
        }
        return socket
    }

    private suspend fun getAsyncInputStream() {
        coroutineScope {
            val deferredStream = async {
                var stream: InputStream? = null
                while (stream == null) {
                    stream = getInputStream()
                    sleep(2000)
                }
                return@async stream
            }
            readStream(deferredStream.await())
        }
    }

    private fun getInputStream(): InputStream? {
        if (!checkPermission()) return null
        var socket: BluetoothSocket? = null
        val bluetoothAdapter: BluetoothAdapter? = getBluetoothAdapter()
        var bluetoothDevice: BluetoothDevice? = null

        bluetoothAdapter?.let {
            bluetoothDevice = getBluetoothDevice(it)
        }
        bluetoothDevice?.let {
            socket = getDeviceSocket(it)
        }
        socket?.let {
            bluetoothAdapter?.cancelDiscovery()
            if (it.isConnected) {
                return it.inputStream
            }
        }
        return null
    }

    private fun readStream(readerStream: InputStream) {
        var codeBuffer = ""
        try {
            while (true) {
                Arrays.fill(readBuffer, 0.toByte())
                val numBytes = readerStream.read(readBuffer)
                val tmp: ByteArray = readBuffer.copyOf(numBytes)
                var done = false
                var data = String(tmp, StandardCharsets.UTF_8)
                if (data.endsWith("\r") || data.endsWith("\n")) {
                    done = true
                    data = data.replace("(\\r|\\n)".toRegex(), "")
                }
                Log.d("BlueT", "Received $numBytes bytes: $data")

                codeBuffer += data
                if (done) {
                    sendIntent(codeBuffer)
                    codeBuffer = ""
                }
            }
        } catch (e: Exception) {
            getCoroutineScopeInputStream()
            println("!!!!!!!!!!!!UROVO!!!!!!!!!!! " + e.message.toString())
        }
    }

    private fun sendIntent(codeBuffer: String) {
        val intent = Intent("ServiceBarcode")
        val instants = LocalBroadcastManager.getInstance(this)
        intent.putExtra("barcode", codeBuffer)
        instants.sendBroadcast(intent)
        Log.d("BlueT", "Received barcode: $codeBuffer")
    }

    private fun createNotification() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Urovo Connection Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager?.createNotificationChannel(serviceChannel)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .build()
        startForeground(NOTIFICATION_ID, notification)
    }




}