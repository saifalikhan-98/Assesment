package com.khan.assesment.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khan.assesment.R
import com.khan.assesment.utils.GetKeyHashForFBLogin
import com.khan.assesment.utils.SendDataPeriodically
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        SendDataPeriodically.periodicWorkRequest()
        GetKeyHashForFBLogin.getKeyHashses(this)

    }

    override fun onBackPressed() {
        finish()

    }

}