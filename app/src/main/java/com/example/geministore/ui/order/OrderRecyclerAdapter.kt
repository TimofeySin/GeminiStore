package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R
import com.example.geministore.services.retrofit.RetrofitDataModelOrderGoods


class OrderRecyclerAdapter(private val arrayModelOrderGoodRetrofits: Array<RetrofitDataModelOrderGoods>) :
    RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameGoods : TextView = itemView.findViewById(R.id.namegoods)
        val weight : TextView = itemView.findViewById(R.id.weight)
        val id : TextView = itemView.findViewById(R.id.id)
        val quantity : TextView = itemView.findViewById(R.id.quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_goods_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameGoods.text = arrayModelOrderGoodRetrofits[position].getNameGoods()
        holder.weight.text = arrayModelOrderGoodRetrofits[position].getWeight()
        holder.id.text = arrayModelOrderGoodRetrofits[position].getId().toString()
       // holder.quantity.text = arrayModelOrderGoodRetrofits[position].getQuantity().toString()
    }

    override fun getItemCount(): Int {
        return arrayModelOrderGoodRetrofits.size
    }



}