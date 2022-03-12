package com.example.geministore.ui.orderList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {

    private var _binding: FragmentOrderListBinding? = null


    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderListViewModel =
            ViewModelProvider(this)[OrderListViewModel::class.java]

        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fetchData(orderListViewModel)
       val swipeRefresh = binding.swiperefresh
        swipeRefresh.setOnRefreshListener {
            fetchData(orderListViewModel)
            orderListViewModel.refreshStatus.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
            }
        }

        val orderList: RecyclerView = binding.orderList
        orderList.layoutManager = LinearLayoutManager(context)
        orderListViewModel.orderListRetrofit.observe(viewLifecycleOwner) {
            orderList.adapter = OrderListRecyclerAdapter(it)
        }
        return root
    }



     fun fetchData(orderListViewModel : OrderListViewModel){
        orderListViewModel.fetchData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

