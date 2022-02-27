package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.databinding.FragmentOrderBinding
import com.example.geministore.ui.BaseFragment


class OrderFragment : BaseFragment() {

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var orderViewModelForKey : OrderViewModel? = null
private var broadcastReceiver : BroadcastReceiver? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
         val orderViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModelForKey = orderViewModel

        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, intent: Intent?) {
                val yourInteger: String? = intent?.getStringExtra("barcode")
                Toast.makeText(p0,yourInteger,Toast.LENGTH_LONG).show()
            }
        }

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = arguments
        bundle?.let {
            val idOrder = it.getString("idOrder")
            val date = it.getString("date")
            Log.d("Bundel", "$idOrder/$date")
            orderViewModel.fetchData(idOrder,date)
        }
//
//        val idOrder : TextView =binding.idOrder
//        orderViewModel.idOrder.observe(viewLifecycleOwner){ idOrder.text = it}
//

        return root
    }

    override fun keyDown(keyCode: Int, event: KeyEvent?): Boolean {
        orderViewModelForKey?.onKeyDown(keyCode,event)

       //* Log.d("codeChar", keyCode.toString())
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onResume() {
        super.onResume()
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(it,  IntentFilter("ServiceBarcode"))
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