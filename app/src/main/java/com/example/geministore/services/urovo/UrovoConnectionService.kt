package com.example.geministore.services.urovo

import android.annotation.SuppressLint
import android.app.*
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.geministore.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*


class UrovoConnectionService : Service() {

    private var currentSocket: BluetoothSocket? = null
    private val idBluetoothSocket = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val CHANNEL_ID = "UrovoConnectionServiceChannel"
    val NOTIFICATION_ID = 1
    private val readBuffer = ByteArray(128)

    override fun onBind(p0: Intent?): IBinder {
        Log.d("BlueT", "onBind");
        startConnection()
        return Binder()
    }

    private fun startConnection() {


        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
               .setContentIntent(pendingIntent)
                .build()
        startForeground(NOTIFICATION_ID, notification)
        connect()
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(CHANNEL_ID,
            "Urovo Connection Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(
            NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }


    @SuppressLint("MissingPermission")
    private fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            val bluetoothManager =
                getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
            bluetoothManager?.let { itBluetoothManager ->
                val bluetoothAdapter = itBluetoothManager.adapter
                bluetoothAdapter?.let { itAdapter ->
                    if (itAdapter.isEnabled) {
                        val deviceAddress =
                            "DC:0D:30:DD:81:70"  //Вот тут надо получать из фрагмента
                        val device = itAdapter.getRemoteDevice(deviceAddress)
                        device?.let { idDevice ->
                            currentSocket =
                                idDevice.createRfcommSocketToServiceRecord(idBluetoothSocket)
                            itAdapter.cancelDiscovery()
                            while (true) {
                                try {
                                    currentSocket?.connect()
                                    Log.d("BlueT", "connect Success")
                                    onConnect()
                                    break
                                } catch (e: IOException) {
                                    Log.d("BlueT", e.toString())
                                    currentSocket?.close()
                                    currentSocket =
                                        idDevice.createRfcommSocketToServiceRecord(idBluetoothSocket)

                                    delay(1000)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onConnect() {
        if (currentSocket?.isConnected == true) {
            val inputStream = currentSocket?.inputStream
            CoroutineScope(Dispatchers.IO).launch {
                readStream(inputStream)
            }
        }
    }

    private fun readStream(readerStream: InputStream?) {
        var codeBuffer = ""
        val intent = Intent("ServiceBarcode")
        val instants = LocalBroadcastManager.getInstance(this)
        while (true) {
            Log.d("BlueT", "Start while")
            Arrays.fill(readBuffer, 0.toByte())
            readerStream?.let {
                val numBytes = it.read(readBuffer)
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
                    Log.d("BlueT", "Received barcode: $codeBuffer")


                    intent.putExtra("barcode", codeBuffer)
                    instants.sendBroadcast(intent)
                    codeBuffer = ""
                }
            }
        }
    }
}