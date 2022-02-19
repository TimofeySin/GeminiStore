package com.example.geministore.ui.orderList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R
import com.example.geministore.databinding.FragmentOrderListBinding
import com.example.geministore.ui.order.OrderFragment

class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null


    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[OrderListViewModel::class.java]

        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.fetchData()

       val swipeRefresh = binding.swiperefresh

        swipeRefresh.setOnRefreshListener {
            homeViewModel.fetchData()
            homeViewModel.refreshStatus.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            }
        }

        val orderList: RecyclerView = binding.orderList
        orderList.layoutManager = LinearLayoutManager(context)
        homeViewModel.orderList.observe(viewLifecycleOwner) {
            orderList.adapter = OrderRecyclerAdapter(it)
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

