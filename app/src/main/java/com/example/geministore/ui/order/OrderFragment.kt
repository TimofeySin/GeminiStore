package com.example.geministore.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.databinding.FragmentOrderBinding
import com.example.geministore.ui.orderList.OrderListViewModel


class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val orderViewModel =
            ViewModelProvider(this)[OrderViewModel::class.java]


        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bundle = arguments
        bundle?.let {
            val idOrder = it.getString("idOrder")
            val date = it.getString("date")
            Log.d("Bundel", "$idOrder/$date")
            orderViewModel.fetchData(idOrder,date)
        }




        val orderGoods: RecyclerView = binding.orderGoods
        orderGoods.layoutManager = LinearLayoutManager(context)
        orderViewModel.orderGoods.observe(viewLifecycleOwner) {
            orderGoods.adapter = OrderRecyclerAdapter(it)
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}