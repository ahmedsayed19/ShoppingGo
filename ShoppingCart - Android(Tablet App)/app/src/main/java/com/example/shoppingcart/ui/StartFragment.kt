package com.example.shoppingcart.ui

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context.BLUETOOTH_SERVICE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.shoppingcart.databinding.FragmentStartBinding
import com.example.shoppingcart.networkAndConnections.BluetoothImpl
import com.example.shoppingcart.networkAndConnections.BluetoothThread
import com.example.shoppingcart.networkAndConnections.MESSAGE_READ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!
    //here we define the shared viewModel for the fragment.
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onResume() {               //Registering bluetooth state broadcast Receiver
        super.onResume()
//        val bluetoothStateFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.bluetoothConnection , bluetoothStateFilter)
//
//        val searchDeviceFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.findDevicesReceiver , searchDeviceFilter)
//
//        val startDiscoveryListener = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.findDevicesReceiver , startDiscoveryListener)
//
//        val finishDiscoveryListener = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.findDevicesReceiver , finishDiscoveryListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(layoutInflater,container,false)

        val bluetoothManager = activity?.applicationContext?.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter : BluetoothAdapter? = bluetoothManager.adapter
        val handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what){
                    MESSAGE_READ -> {
                        val receivedMessage = msg.obj.toString()
                        Log.d("bluetoothTag",receivedMessage)
                    }
                }
            }
        }
        if (bluetoothAdapter != null){
            sharedViewModel.bluetooth = BluetoothThread(requireActivity().applicationContext, bluetoothAdapter, handler)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.bluetooth?.let { it ->
            it.enableBluetooth()
//            it.startDiscovery()
            it.ConnectThread().start()
//            lifecycleScope.launch(Dispatchers.Default) {
//                val inputStream = async { it.ConnectThread().connect()}
//                inputStream.await()?.let { it1 -> it.ConnectThread().readData(it1) }
//            }
        }

        binding.button.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToCartFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onPause() {                     //here we stop the broadcast receiver so we prevent memory leaks
        super.onPause()
//        requireActivity().unregisterReceiver(sharedViewModel.bluetooth?.bluetoothConnection)
//        requireActivity().unregisterReceiver(sharedViewModel.bluetooth?.findDevicesReceiver)
    }
}