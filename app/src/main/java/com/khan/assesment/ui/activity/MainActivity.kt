package com.khan.assesment.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.AccessToken
import com.khan.assesment.R
import com.khan.assesment.utils.AppUtils
import com.khan.assesment.utils.GetKeyHashForFBLogin
import com.khan.assesment.utils.NotificationBuilder.Companion.createNotificationChannel
import com.khan.assesment.utils.NotificationBuilder.Companion.sendNotification
import com.khan.assesment.utils.SendDataPeriodically

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*createNotificationChannel(this)
        sendNotification(this)*/
        SendDataPeriodically.periodicWorkRequest()
        GetKeyHashForFBLogin.getKeyHashses(this)

    }

    override fun onBackPressed() {
        finish()

    }

}