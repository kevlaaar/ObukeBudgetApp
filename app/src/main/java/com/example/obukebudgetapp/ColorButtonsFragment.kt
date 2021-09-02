package com.example.obukebudgetapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class ColorButtonsFragment : Fragment(R.layout.fragment_color_buttons) {
    var transactionService: TransactionService? = null

    lateinit var redButton: Button
    lateinit var yellowButton: Button
    lateinit var blueButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yellowButton = view.findViewById(R.id.yellowButton)
        blueButton = view.findViewById(R.id.blueButton)
        redButton = view.findViewById(R.id.redButton)

        bindTransactionService()

    }

    private val transactionServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, iBinder: IBinder?) {
            val binder = iBinder as TransactionService.TransactionServiceBinder
            transactionService = binder.service
            setListeners()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    private fun setListeners() {
        transactionService?.let { service ->
            yellowButton.setOnClickListener{
                service.displayColoredNotification(R.color.yellow, 9)
            }
            blueButton.setOnClickListener{
                service.displayColoredNotification(R.color.blue, 11)
            }
            redButton.setOnClickListener{
                service.displayColoredNotification(R.color.red, 13)
            }
        }
    }

    private fun bindTransactionService() {
        val intent = Intent(requireContext(), TransactionService::class.java)
        requireActivity().applicationContext.bindService(intent, transactionServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unbindTransactionService() {
        Intent(requireContext(), TransactionService::class.java)
        requireActivity().applicationContext.unbindService(transactionServiceConnection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindTransactionService()
    }
}