package com.example.caloriecounterapp

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat


val NOTIFICATION_ID = 1
val CHANNEL_ID = "channel1"



class NotificationHandler : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification : Notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_water_drop_24)
            .setContentTitle("Drink Water")
            .setContentText("Your scheduled water intake time is here")
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)

    }


}