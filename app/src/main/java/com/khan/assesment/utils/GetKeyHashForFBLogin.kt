package com.khan.assesment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

class GetKeyHashForFBLogin {

    @Suppress("DEPRECATION")
    companion object{

        @SuppressLint("PackageManagerGetSignatures")
        fun getKeyHashses(context: Context){

            try{
                val info : PackageInfo = context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
                )
                for(signatue in info.signatures ){

                    val msgDigest : MessageDigest = MessageDigest.getInstance("SHA")
                    msgDigest.update(signatue.toByteArray())
                    val keyHashes = String(Base64.encode(msgDigest.digest(), 0))

                    Log.d("KeyHashes", "Facebook Key Hashes " + keyHashes.toString())
                }

            }catch (e: Exception){

            }

        }

    }
}