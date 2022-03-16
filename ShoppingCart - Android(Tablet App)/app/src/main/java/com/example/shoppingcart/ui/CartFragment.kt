package com.example.shoppingcart.ui

import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.shoppingcart.R
import com.example.shoppingcart.adapters.OffersAdapter
import com.example.shoppingcart.adapters.ProductAdapter
import com.example.shoppingcart.databinding.FragmentCartBinding
import com.example.shoppingcart.ui.dialogs.ConfirmationDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var _binding : FragmentCartBinding? = null
    private val binding get() = _binding!!
    // here we define the shared viewModel for the fragment.
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val adapter = ProductAdapter()

    override fun onResume() {                      //Registering bluetooth state broadcast Receiver
        super.onResume()
//        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
//        requireActivity().registerReceiver(sharedViewModel.bluetooth?.bluetoothConnection , filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container,false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the SharedViewModel
        binding.viewModel = sharedViewModel

        adapter.setList(sharedViewModel.products.value)
        binding.userProductsRecyclerView.adapter = adapter
        binding.offersRecyclerView.adapter = OffersAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.products.observe(viewLifecycleOwner) {
            adapter.setList(sharedViewModel.products.value)
        }

        binding.btnProceed.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val product = async { sharedViewModel.getProduct() }
                ConfirmationDialog(product.await(),sharedViewModel.list).show(parentFragmentManager,"ConfirmationDialog")
            }
        }

        binding.marketBtn.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val product = async { sharedViewModel.getProduct2() }
//                ConfirmationDialog(product.await(),sharedViewModel.list).show(parentFragmentManager,"ConfirmationDialog")
//            }
            val action = CartFragmentDirections.actionCartFragmentToBillFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onPause() {
        super.onPause()
//        requireActivity().unregisterReceiver(sharedViewModel.bluetooth?.bluetoothConnection)
    }
}