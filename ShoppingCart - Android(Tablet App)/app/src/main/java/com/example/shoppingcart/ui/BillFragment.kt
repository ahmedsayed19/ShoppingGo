package com.example.shoppingcart.ui

import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.BillAdapter
import com.example.shoppingcart.databinding.FragmentBillBinding

class BillFragment : Fragment() {

    private var _binding : FragmentBillBinding? = null
    //this getter declared so we could call the binding without all safe calls.
    private val binding get() = _binding!!
    //here we define the shared viewModel for the fragment.
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
//        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.bluetoothConnection , filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill, container,false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = sharedViewModel
        binding.billRecyclerView.adapter = BillAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.generateQR(binding.qrImageView)
    }

    override fun onPause() {                     //here we stop the broadcast receiver so we prevent memory leaks
        super.onPause()
//        requireActivity().unregisterReceiver(sharedViewModel.bluetooth?.bluetoothConnection)
    }
}