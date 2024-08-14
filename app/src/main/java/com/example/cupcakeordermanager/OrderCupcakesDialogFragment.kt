package com.example.cupcakeordermanager

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels

class OrderCupcakesDialogFragment : DialogFragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_cupcakes_dialog, container, false)

        val customerNameEditText: EditText = view.findViewById(R.id.customer_name)
        val phoneNumberEditText: EditText = view.findViewById(R.id.phone_number)
        val quantityEditText: EditText = view.findViewById(R.id.quantity)
        val flavorSpinner: Spinner = view.findViewById(R.id.cupcake_flavor)
        val saveButton: Button = view.findViewById(R.id.save_button)

        // Initialize NotificationHelper
        notificationHelper = NotificationHelper(requireContext())

        // Populate flavor spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cupcake_flavors,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            flavorSpinner.adapter = adapter
        }

        saveButton.setOnClickListener {
            val customerName = customerNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val quantity = quantityEditText.text.toString()
            val flavor = flavorSpinner.selectedItem.toString()

            if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() && quantity.isNotEmpty()) {
                val newOrder = Order(customerName, flavor, "Cupcakes - Quantity: $quantity")
                orderViewModel.addOrder(newOrder)
                // Send notification
                notificationHelper.sendNotification(customerName, quantity)
                Toast.makeText(context, "Order Saved: $customerName, $quantity, $flavor", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(context, "Customer name, phone number, and quantity cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }
}
