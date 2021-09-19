package com.khan.assesment.utils

import android.content.Context
import android.content.SharedPreferences
import com.khan.assesment.Constants.Companion.ACCESSTOKEN
import com.khan.assesment.Constants.Companion.BusinessName
import com.khan.assesment.Constants.Companion.ISLOGGEDIN
import com.khan.assesment.Constants.Companion.PAGEID
import com.khan.assesment.Constants.Companion.USERID

class PreferenceHelper {

    companion object{
        private fun preferences (context: Context): SharedPreferences = context.getSharedPreferences("default",0)

        fun setAccessToken(context: Context,accessToken : String){
            preferences(context).edit()
                .putString(ACCESSTOKEN,accessToken)
                .apply()
        }

        fun getAccessToken(context: Context) : String? = preferences(context).getString(ACCESSTOKEN,"notLoggedIn")

        fun setBusinessName(context: Context, businessName : String){
            preferences(context).edit()
                    .putString(BusinessName,businessName)
                    .apply()
        }

        fun getBusinessName(context: Context) : String? = preferences(context).getString(BusinessName,"notLoggedIn")

        fun setUserId(context: Context,userId : String){
            preferences(context).edit()
                .putString(USERID,userId)
                .apply()
        }

        fun getUserId(context: Context) : String? = preferences(context).getString(USERID,"notLoggedIn")


        fun setPageID(context: Context,postId : String){
            preferences(context).edit()
                .putString(PAGEID,postId)
                .apply()
        }

        fun getPageID(context: Context) : String? = preferences(context).getString(PAGEID,"notLoggedIn")


        fun setUserStatus(context: Context,isLoggedIn : Boolean){
            preferences(context).edit()
                .putBoolean(ISLOGGEDIN,isLoggedIn)
                .apply()
        }

        fun getAccessStatus(context: Context) : Boolean = preferences(context).getBoolean(ISLOGGEDIN,false)

    }
}