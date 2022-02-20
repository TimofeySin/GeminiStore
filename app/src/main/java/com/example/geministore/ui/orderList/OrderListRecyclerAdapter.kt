package com.example.geministore.ui.orderList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R
import com.example.geministore.data.retrofit.RetrofitDataModelOrderList


class OrderListRecyclerAdapter(private val arrayModelOrderListRetrofit: Array<RetrofitDataModelOrderList>) :
    RecyclerView.Adapter<OrderListRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idOrder : TextView = itemView.findViewById(R.id.id_order)
        val date : TextView  = itemView.findViewById(R.id.date_order)
        val manger : TextView = itemView.findViewById(R.id.manager)
        val deliveryTime : TextView  = itemView.findViewById(R.id.time_delivery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.idOrder.text = arrayModelOrderListRetrofit[position].getIdOrder()
        holder.date.text = arrayModelOrderListRetrofit[position].getDate()
        holder.manger.text = arrayModelOrderListRetrofit[position].getManger()
        holder.deliveryTime.text = arrayModelOrderListRetrofit[position].getDeliveryTime()

        holder.itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {

                    val bundle = bundleOf(Pair("idOrder",arrayModelOrderListRetrofit[position].getIdOrder()),
                        Pair("date",arrayModelOrderListRetrofit[position].getDate()))

                    view.findNavController().navigate(R.id.nav_order,bundle)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return arrayModelOrderListRetrofit.size
    }
}