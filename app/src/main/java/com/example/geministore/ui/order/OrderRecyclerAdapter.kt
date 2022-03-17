package com.example.geministore.ui.order

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.example.geministore.R
import com.example.geministore.services.glide.ServiceGlide
import com.example.geministore.ui.order.orderModels.DataModelOrderGoods


class OrderRecyclerAdapter(private val arrayModelOrderGood: MutableList<DataModelOrderGoods>, fragmentModel :OrderViewModel) :
    RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder>() {
    val fragmentModelNew = fragmentModel
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val priceGoods: TextView = itemView.findViewById(R.id.good_price)
        val totalGoods: TextView = itemView.findViewById(R.id.good_total)
        val nameGoods: TextView = itemView.findViewById(R.id.good_description)
        val commentGoods: TextView = itemView.findViewById(R.id.good_comment)
        val imageGoods: ImageView = itemView.findViewById(R.id.good_image)
        val buttonClear: Button = itemView.findViewById(R.id.button_clear)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_goods_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.nameGoods.text = arrayModelOrderGood[position].nameGoods
        holder.priceGoods.text = arrayModelOrderGood[position].priceGoods.toString()
        holder.totalGoods.text = quantityText(context, arrayModelOrderGood[position])
        if (arrayModelOrderGood[position].commentGoods == "") {
            holder.commentGoods.visibility = GONE
        } else {
            holder.commentGoods.text = arrayModelOrderGood[position].commentGoods
        }
        ServiceGlide().getImage(arrayModelOrderGood[position].id, holder.imageGoods, context)
        holder.buttonClear.setOnClickListener {
            arrayModelOrderGood[position].completeGoods = 0F
            fragmentModelNew.updateAdapter(1,position)
        }
    }

    override fun getItemCount(): Int {
        return arrayModelOrderGood.size
    }

    private fun quantityText(context: Context, orderGoods: DataModelOrderGoods): Spannable {
        val quantityFull = orderGoods.totalGoods.toString()
        val quantityComplete = orderGoods.completeGoods.toString()

        val highlightingColor = if (orderGoods.completeGoods >= orderGoods.totalGoods) {
            context.getColor(R.color.green)
        } else {
            context.getColor(R.color.orange)
        }

        return SpannableStringBuilder()
            .append("Собрано ")
            .color(highlightingColor) { append("$quantityComplete из $quantityFull") }
    }

}