package com.khan.assesment.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.facebook.AccessToken
import com.khan.assesment.R
import com.khan.assesment.databinding.LoadingdialogBinding

@Suppress("DEPRECATION")
class AppUtils {

    companion object{
        fun showToastMsg(msg : String,context: Context){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val networks = cm.allNetworks
                for (n in networks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo != null && nInfo.isConnected) return true
                }
            } else {
                val networks = cm.allNetworkInfo
                for (nInfo in networks) {
                    if (nInfo != null && nInfo.isConnected) return true
                }
            }
            return false
        }

        fun showProgress(activity: Activity): Dialog {
            val binding = LoadingdialogBinding.inflate(LayoutInflater.from(activity.applicationContext)).root
            val dialog = Dialog(activity)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()?.setBackgroundDrawable(
                ColorDrawable(0)
            )
            dialog.setContentView(binding);

            dialog.setCancelable(false)
            dialog.show()
            return dialog
        }

        fun checkLoginStatus():Boolean{
            val accessToken = AccessToken.getCurrentAccessToken()
            val isLoggedIn = accessToken != null && !accessToken.isExpired
            return isLoggedIn
        }
    }
}