package com.example.shoppingcart.networkAndConnections

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*


val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // hc-05 UUID
private const val TAG = "bluetoothTag"
const val MESSAGE_READ: Int = 0

class BluetoothThread(
    private var context: Context,
    private var adapter: BluetoothAdapter,
    private val handler: Handler
) : BluetoothOps {

    private var device: BluetoothDevice = adapter.getRemoteDevice("98:D3:81:FD:7D:AB")
    @SuppressLint("MissingPermission")
    var mmSocket: BluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)

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
    inner class ConnectThread : Thread(){

        override fun run() {
            super.run()
            try {
                adapter.cancelDiscovery()
                if (!mmSocket.isConnected){
                    mmSocket.connect()
                }

                if (mmSocket.isConnected){
                    Log.d(TAG,mmSocket.remoteDevice.name)
                    Log.d(TAG,mmSocket.inputStream.toString())
                    ConnectedThread().start()
                }
            }catch (e: Exception){
                Log.d(TAG,e.toString())
            }
        }

        fun cancel(){
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the client socket", e)
            }
        }
    }

    inner class ConnectedThread : Thread(){
        private val mmInStream = mmSocket.inputStream
        private val mmBuffer = ByteArray(1024)

        override fun run() {
            super.run()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                // Read from the InputStream.
                try {
                    mmInStream.read(mmBuffer)
                } catch (e: IOException) {
                    Log.d(TAG, "Input stream was disconnected", e)
                    break
                }
//                var numBytes = 0
                Log.d(TAG,mmBuffer[0].toString())

                // Send the obtained bytes to the UI activity.
//                val readMsg = handler.obtainMessage(
//                    MESSAGE_READ, mmBuffer.size, -1,
//                    mmBuffer)
//                readMsg.sendToTarget()
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the connect socket", e)
            }
        }
    }

}
