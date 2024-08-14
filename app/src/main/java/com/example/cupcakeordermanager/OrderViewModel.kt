package com.example.cupcakeordermanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<MutableList<Order>>(mutableListOf())
    val orders: LiveData<MutableList<Order>> = _orders

    fun addOrder(order: Order) {
        _orders.value?.add(order)
        _orders.value = _orders.value // Trigger observers
    }
}