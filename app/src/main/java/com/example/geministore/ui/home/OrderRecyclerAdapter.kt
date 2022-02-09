package com.example.geministore.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R


class OrderRecyclerAdapter(private val orderArray: Array<List<String>>) :
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
        holder.idOrder.text = orderArray[position][0]
        holder.date.text = orderArray[position][1]
        holder.manger.text = orderArray[position][2]
        holder.deliveryTime.text = orderArray[position][3]

        holder.itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val id_order = view.findViewById<TextView>(R.id.id_order)
                    Log.d("Click", id_order.text as String)
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("Click","Up")
                }
            }
            true
        }


    }

    override fun getItemCount(): Int {
        return orderArray.size
    }
}