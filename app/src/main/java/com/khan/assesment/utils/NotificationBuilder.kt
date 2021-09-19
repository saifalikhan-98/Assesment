package com.khan.assesment.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khan.assesment.Constants.Companion.CHANNELID

import com.khan.assesment.Constants.Companion.NOTIFICATIONCHANNELID
import com.khan.assesment.R
import com.khan.assesment.presentation.ui.MainActivity

class NotificationBuilder {


    companion object{



        fun createNotificationChannel(context: Context){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                val name = "Assessment"
                val descriptionText = "Notification triggered from work manager"
                val importance = NotificationManager.IMPORTANCE_DEFAULT

                val channel = NotificationChannel(CHANNELID,name,importance).apply {
                    description = descriptionText

                }
                val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

            }
        }

        fun sendNotification(context: Context){


            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)





            var builder = NotificationCompat.Builder(context, CHANNELID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Assessment")
                    .setContentText("Notification triggered from work manager on api call")
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText("Notification triggered from work manager"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(context)){

                notify(NOTIFICATIONCHANNELID,builder.build())
            }
        }
    }
}