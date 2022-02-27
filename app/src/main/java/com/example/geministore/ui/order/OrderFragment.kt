package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.databinding.FragmentOrderBinding
import com.example.geministore.ui.BaseFragment
import com.example.geministore.ui.orderList.OrderListRecyclerAdapter


class OrderFragment : BaseFragment() {

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var broadcastReceiver: BroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val orderViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                val barcode: String? = intent?.getStringExtra("barcode")
                barcode?.let {
                    orderViewModel.responseCode(it)
                }
            }
        }

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = arguments
        bundle?.let {
            val idOrder = it.getString("idOrder")
            orderViewModel.fetchData(idOrder)
        }

        val commentClient : TextView =binding.commentClient
        orderViewModel.commentClient.observe(viewLifecycleOwner){ commentClient.text = it}

        val commentOrder : TextView =binding.commentOrder
        orderViewModel.commentOrder.observe(viewLifecycleOwner){ commentOrder.text = it}

        val goodsList: RecyclerView = binding.orderGoods
        goodsList.layoutManager = LinearLayoutManager(context)
        orderViewModel.orderGoods.observe(viewLifecycleOwner) {
            goodsList.adapter = OrderRecyclerAdapter(it)
        }



        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(it, IntentFilter("ServiceBarcode"))
        }
    }


    override fun onPause() {
        super.onPause()
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(it)
        }
    }
}