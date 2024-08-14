package com.example.cupcakeordermanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class OrderListFragment : Fragment() {
    private lateinit var orderAdapter: OrderAdapter
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val emptyTextView: TextView = view.findViewById(R.id.empty_text_view)

        recyclerView.layoutManager = LinearLayoutManager(context)

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            if (orders.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyTextView.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                orderAdapter = OrderAdapter(orders)
                recyclerView.adapter = orderAdapter
            }
        }

        return view
    }
}
