package com.example.obukebudgetapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TransactionService : Service() {

    private val transactionBinder = TransactionServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationBuilder = NotificationCompat.Builder(
            applicationContext, createChannelId()
        ).setContentTitle("Obuke Budget app")
            .setContentText("Pratim transakcije...")
            .setSmallIcon(R.drawable.icon_profile)




        startForeground(10, notificationBuilder.build())
        return START_NOT_STICKY
    }

    private fun createChannelId(): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "transaction_channel_id",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = NotificationManagerCompat.from(applicationContext)
            notificationManager.createNotificationChannel(channel)
            channel.id
        } else {
            ""
        }
    }

    fun displayColoredNotification (color: Int, notificationId: Int){
        val notificationBuilder = NotificationCompat.Builder(
            applicationContext, createChannelId()
        ).setContentTitle("Obuke Budget app")
            .setContentText("Pratim transakcije...")
            .setColor(color)
            .setSmallIcon(R.drawable.icon_profile)

        NotificationManagerCompat.from(applicationContext).notify(notificationId,notificationBuilder.build())
    }



    override fun onBind(p0: Intent?): IBinder? {
        return transactionBinder
    }

    inner class TransactionServiceBinder : Binder() {
        val service: TransactionService
            get() = this@TransactionService
    }

}