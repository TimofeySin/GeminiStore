package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
         val orderViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModelForKey = orderViewModel

        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = arguments
        bundle?.let {
            val idOrder = it.getString("idOrder")
            val date = it.getString("date")
            Log.d("Bundel", "$idOrder/$date")
            orderViewModel.fetchData(idOrder,date)
        }

        val idOrder : TextView =binding.idOrder
        orderViewModel.idOrder.observe(viewLifecycleOwner){ idOrder.text = it}
        val deliveryTime : TextView =binding.deliveryTime
        orderViewModel.deliveryTime.observe(viewLifecycleOwner){ deliveryTime.text = it}
        val manger : TextView =binding.manger
        orderViewModel.manger.observe(viewLifecycleOwner){ manger.text = it}
        val date : TextView =binding.date
        orderViewModel.idOrder.observe(viewLifecycleOwner){ date.text = it}

        val orderGoods: RecyclerView = binding.orderGoods
        orderGoods.layoutManager = LinearLayoutManager(context)
        orderViewModel.orderGoodsRetrofit.observe(viewLifecycleOwner) {
            orderGoods.adapter = OrderRecyclerAdapter(it)
        }


        val startScan : Button = binding.startScan
        startScan.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (startScan.text == "Начать сканирование"){
                        startScan.text = "Остановить сканирование"
                        orderViewModel.setStartScan(true)
                    } else {
                        orderViewModel.setStartScan(false)
                        startScan.text = "Начать сканирование"}
                }
            }
            true
        }

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
}