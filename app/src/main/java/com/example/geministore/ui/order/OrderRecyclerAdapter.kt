package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R


class OrderRecyclerAdapter(private val arrayModelOrderGoods: Array<DataModelOrderGoods>) :
    RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameGoods : TextView = itemView.findViewById(R.id.namegoods)
        val weight : TextView = itemView.findViewById(R.id.weight)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_goods_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameGoods.text = arrayModelOrderGoods[position].getNameGoods()
        holder.weight.text = arrayModelOrderGoods[position].getWeight()

    }

    override fun getItemCount(): Int {
        return arrayModelOrderGoods.size
    }



}