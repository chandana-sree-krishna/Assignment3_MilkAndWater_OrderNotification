package com.example.cupcakeordermanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.customerNameTextView.text = order.customerName
        holder.cupcakeFlavorTextView.text = order.cupcakeFlavor
        holder.orderTypeTextView.text = order.orderType
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerNameTextView: TextView = itemView.findViewById(R.id.customer_name)
        val cupcakeFlavorTextView: TextView = itemView.findViewById(R.id.cupcake_flavor)
        val orderTypeTextView: TextView = itemView.findViewById(R.id.order_type)
    }
}