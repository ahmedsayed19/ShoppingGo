package com.example.shoppingcart.networkAndConnections

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.*
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.util.*


val MY_UUID2: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // hc-05 UUID
private const val TAG = "bluetoothTag"
//const val MESSAGE_READ: Int = 0

class BluetoothImpl(private var context: Context,
                    private var adapter: BluetoothAdapter,
                    private val handler: Handler) : BluetoothOps {

    var device: BluetoothDevice? = adapter.getRemoteDevice("98:D3:81:FD:7D:AB")

    override fun checkPermission(): Boolean {
        return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        ) {
            println()
            false
        } else {
            true
        }
    }

    override fun enableBluetooth() {
        Log.d(TAG, "inEnable")
        if ( !adapter.isEnabled) {
            if (!checkPermission()) {
                adapter.enable()
            }
            Toast.makeText(context, "Bluetooth activated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Bluetooth is already active", Toast.LENGTH_SHORT).show()
        }
    }

    override fun startDiscovery() {
        checkPermission()
        adapter.startDiscovery()
        Log.d(TAG, "inDiscovery")
    }

    @SuppressLint("MissingPermission")
    inner class ConnectThread{
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device?.createRfcommSocketToServiceRecord(MY_UUID2)
        }
        private var mmInStream: InputStream? = mmSocket?.inputStream
        private var mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream

        fun connect() : InputStream? {
            try {
                adapter.cancelDiscovery()
                mmSocket?.connect()

                if (mmSocket?.isConnected == true){
                    mmSocket?.remoteDevice?.name?.let { Log.d(TAG, it) }
                    Log.d(TAG,mmInStream.toString())
                }
            }catch (e: Exception){
                Log.d(TAG,e.toString())
            }
            return mmSocket?.inputStream
        }

        fun readData(inputStream: InputStream?){
            var numBytes = 0   // bytes returned from read()
            inputStream?.let {
                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    // Read from the InputStream.
                    try {
                        mmBuffer[numBytes] = inputStream.read().toByte()
                        var readMessage: String
                        if (mmBuffer[numBytes].equals("/n")) {
                            readMessage = String(mmBuffer, 0, numBytes)
                            Log.e("MicroController Message", readMessage)
                            handler.obtainMessage(MESSAGE_READ, readMessage).sendToTarget()
                            numBytes = 0
                        } else {
                            numBytes++
                        }
                    } catch (e: IOException) {
                        Log.d(TAG, "Input stream was disconnected",e)
                        e.printStackTrace()
                        break
                    }
                }
            }
        }

        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////
    // broadcast receiver to check the state of bluetooth whether it is open or not.
    val bluetoothConnection = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(EXTRA_STATE, ERROR)) {
                    STATE_OFF -> {
                        enableBluetooth()
                    }
                    STATE_TURNING_ON -> {
                        Toast.makeText(context,"Bluetooth is turning on please wait", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // broadcast receiver to check if the search found new device.
    val findDevicesReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            checkPermission()
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val foundDevice: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = foundDevice?.name
                    val deviceAddress = foundDevice?.address
//                    val uuids = foundDevice?.uuids
                    if (deviceName != null && deviceAddress != null ) {
                        Log.d(TAG,deviceName)
                        Log.d(TAG,deviceAddress)
                       // Log.d(TAG, uuids[0].uuid.toString())
                    }
                }
                ACTION_DISCOVERY_STARTED -> {
                    Log.d(TAG, "Started Discovery")
                }
                ACTION_DISCOVERY_FINISHED -> {
                    Log.d(TAG, "Finished Discovery")
                }
            }
        }
    }
}