package ru.timofeysin.geministore.ui.orderList

import android.annotation.SuppressLint
import android.content.Context



import android.text.Spannable
import android.text.SpannableStringBuilder

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import androidx.core.os.bundleOf
import androidx.core.text.color
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

import ru.timofeysin.geministore.R



class OrderListRecyclerAdapter(private val arrayModelOrderListRetrofit: MutableList<DataModelOrderList>) :
    RecyclerView.Adapter<OrderListRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameAndNumber : TextView = itemView.findViewById(R.id.name_and_order)
        val deliveryTime : TextView  = itemView.findViewById(R.id.time_delivery)
        val commentText : TextView  = itemView.findViewById(R.id.comment_text)
        val quantityGoods : TextView  = itemView.findViewById(R.id.quantity_goods)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val nameAndNumber = arrayModelOrderListRetrofit.elementAt(position).clientName+", " +arrayModelOrderListRetrofit.elementAt(position).idOrder
        holder.nameAndNumber.text = nameAndNumber
        holder.deliveryTime.text = arrayModelOrderListRetrofit.elementAt(position).deliveryTime
        holder.commentText.text = arrayModelOrderListRetrofit.elementAt(position).comment
        holder.quantityGoods.text = quantityText(context,arrayModelOrderListRetrofit.elementAt(position))




        holder.itemView.setOnClickListener {
            val bundle =
                bundleOf(Pair("idOrder", arrayModelOrderListRetrofit.elementAt(position).idOrder))
            it.findNavController().navigate(R.id.nav_order, bundle)
        }
    }


    private fun quantityText(context:Context, orderItem: DataModelOrderList): Spannable {
        val startText = context.getText(R.string.quantity_goods).toString()
        val quantityFull = orderItem.quantityFull.toString()
        val quantityComplete = context
            .getText(R.string.quantity_complete).toString() + orderItem.quantityComplete.toString()

        val highlightingColor = if (orderItem.quantityFull == orderItem.quantityComplete){
            context.getColor(R.color.green)
        }else{
            context.getColor(R.color.orange)
        }

        return SpannableStringBuilder()
            .append("$startText$quantityFull (")
            .color(highlightingColor) { append(quantityComplete) }
            .append(")")
    }
    override fun getItemCount(): Int {
        return arrayModelOrderListRetrofit.size
    }
}