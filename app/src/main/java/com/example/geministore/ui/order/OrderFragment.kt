package com.example.geministore.ui.order

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.databinding.FragmentOrderBinding



class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null

    private val binding get() = _binding!!
    private var orderViewModelGlobal: OrderViewModel? = null
    private var broadcastReceiver: BroadcastReceiver? = null
    private var alertAnimation: ObjectAnimator? = null
    private var idOrder: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val orderViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModelGlobal = orderViewModel

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
            idOrder = it.getString("idOrder").toString()
            orderViewModel.fetchData(idOrder)
        }
        observeView(orderViewModel)

        binding.cancelAlertButton.setOnClickListener {
            binding.alert.alpha = 0F
        }

        binding.addAlertButton.setOnClickListener {
            orderViewModel.addQuantityGoods()
            binding.alert.alpha = 0F
        }

        return root
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun observeView(orderViewModel: OrderViewModel) {
        val commentClient: TextView = binding.commentClient
        orderViewModel.commentClient.observe(viewLifecycleOwner) { commentClient.text = it }

        val commentOrder: TextView = binding.commentOrder
        orderViewModel.commentOrder.observe(viewLifecycleOwner) { commentOrder.text = it }


        val goodsList: RecyclerView = binding.orderGoods
        goodsList.layoutManager = LinearLayoutManager(context)
        orderViewModel.orderGoods.observe(viewLifecycleOwner) {
            goodsList.adapter = orderViewModelGlobal?.let { it1 -> OrderRecyclerAdapter(it, it1) }
        }
        orderViewModel.updaterAdapter.observe(viewLifecycleOwner) {
            when (it.action) {
                1 -> goodsList.adapter!!.notifyItemChanged(it.position)
                2 -> goodsList.adapter!!.notifyItemInserted(it.position)
            }
            goodsList.adapter!!.notifyDataSetChanged()
        }

        val progressBar: ProgressBar = binding.progressBar
        orderViewModel.progressBar.observe(viewLifecycleOwner) { progressBar.visibility = it }


        val buttonLayout: LinearLayout = binding.buttonLayout
        val headAlert: TextView = binding.headAlert
        val descAlert: TextView = binding.descAlert
        val totalAlert: TextView = binding.totalAlert
        val alert: CardView = binding.alert
        alertAnimation = ObjectAnimator.ofFloat(alert, View.ALPHA, 1F, 0F).apply {
            startDelay = 1000
            duration = 2000
        }
        orderViewModel.alertModel.observe(viewLifecycleOwner) {
            buttonLayout.visibility = it.buttonVisible
            headAlert.text = it.headAlert
            descAlert.text = it.descAlert
            totalAlert.text = it.totalAlert
            alert.setCardBackgroundColor(it.colorAlert)

            alertAnimation?.let { itAnimation ->
                when {
                    it.buttonVisible == 0 -> {
                        itAnimation.cancel()
                        alert.alpha = 1F
                    }
                    itAnimation.isRunning || it.alertVisible == 0 -> {
                        alert.alpha = 1F
                        itAnimation.start()
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = idOrder
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(it, IntentFilter("ServiceBarcode"))
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.actionBar?.title = idOrder
    }

    override fun onPause() {
        super.onPause()
        orderViewModelGlobal?.saveData()
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(it)
        }
    }
}