package com.example.cupcakeordermanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class OrderCakeDialogFragment : DialogFragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_cake_dialog, container, false)

        val radioGroupSize: RadioGroup = view.findViewById(R.id.radio_group_size)
        val customerNameEditText: EditText = view.findViewById(R.id.customer_name)
        val phoneNumberEditText: EditText = view.findViewById(R.id.phone_number)
        val flavorSpinner: Spinner = view.findViewById(R.id.cake_flavor)
        val messageEditText: EditText = view.findViewById(R.id.message)
        val saveButton: Button = view.findViewById(R.id.save_button)

        // Initialize NotificationHelper
        notificationHelper = NotificationHelper(requireContext())

        // Populate flavor spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cake_flavors,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            flavorSpinner.adapter = adapter
        }

        saveButton.setOnClickListener {
            val selectedSizeId = radioGroupSize.checkedRadioButtonId
            val selectedSize: String = if (selectedSizeId != -1) {
                view.findViewById<RadioButton>(selectedSizeId).text.toString()
            } else {
                "Not selected"
            }

            val customerName = customerNameEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()
            val flavor = flavorSpinner.selectedItem.toString()
            val message = messageEditText.text.toString().trim()

            if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() && selectedSize != "Not selected") {
                val newOrder = Order(customerName, flavor, "Cake - Size: $selectedSize, Message: $message")
                orderViewModel.addOrder(newOrder)
                // Send notification
                notificationHelper.sendNotification(customerName, selectedSize)
                Toast.makeText(context, "Order Saved: $customerName, $selectedSize, $flavor", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(context, "Please fill all fields and select a size.", Toast.LENGTH_SHORT).show()
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
