package com.khan.assesment

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEvent
import com.facebook.appevents.AppEventsLogger

class BaseClass : Application() {

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.fullyInitialize()
        AppEventsLogger.activateApp(this)

    }



}