package com.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter (var context: Context, var list: ArrayList<CustomModel>) :
    RecyclerView.Adapter<ItemsAdapter.Viewholder>() {

    var onClick: ItemsAdapter.onItemClick? = null

    fun setOnClickListener(onItemClick: onItemClick) {
        this.onClick = onItemClick
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvProductName = itemView.findViewById<TextView>(R.id.tvProductName)
        var tvProductPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)
        var tvFoodQuantity = itemView.findViewById<TextView>(R.id.tvQuantity)
        var ivMinus = itemView.findViewById<ImageView>(R.id.ivMinus)
        var ivPlus = itemView.findViewById<ImageView>(R.id.ivPlus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.tvProductName.text = list[position].name
        holder.tvProductPrice.text = "₹" + list[position].price.toString()
        holder.tvFoodQuantity.text = list[position].quantity.toString()

        holder.tvProductPrice.setOnClickListener(View.OnClickListener {
            onClick!!.onItemClickListener(position, list[position],1)
        })

        holder.ivMinus.setOnClickListener(View.OnClickListener {
            onClick!!.onItemClickListener(position, list[position],3)
        })
        holder.ivPlus.setOnClickListener(View.OnClickListener {
            onClick!!.onItemClickListener(position, list[position],2)
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface onItemClick {
        fun onItemClickListener(pos: Int, data: CustomModel, i:Int)
    }
}