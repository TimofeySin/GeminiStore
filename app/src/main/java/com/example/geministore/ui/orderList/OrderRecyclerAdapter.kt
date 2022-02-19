package com.example.geministore.ui.orderList

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R
import com.example.geministore.data.retrofit.DataModelOrderList


class OrderRecyclerAdapter(private val ArrayModelOrderList: Array<DataModelOrderList>) :
    RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idOrder : TextView = itemView.findViewById(R.id.id_order)
        val date : TextView  = itemView.findViewById(R.id.date_order)
        val manger : TextView = itemView.findViewById(R.id.manager)
        val deliveryTime : TextView  = itemView.findViewById(R.id.time_delivery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.idOrder.text = ArrayModelOrderList[position].getIdOrder()
        holder.date.text = ArrayModelOrderList[position].getDate()
        holder.manger.text = ArrayModelOrderList[position].getManger()
        holder.deliveryTime.text = ArrayModelOrderList[position].getDeliveryTime()

        holder.itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val idOrder = view.findViewById<TextView>(R.id.id_order)
                    Log.d("Click", idOrder.text as String)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return ArrayModelOrderList.size
    }
}