package com.khan.assesment.utils

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.models.RequestModel
import com.khan.assesment.utils.NotificationBuilder.Companion.createNotificationChannel
import com.khan.assesment.utils.NotificationBuilder.Companion.sendNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class SendDataPeriodically(val appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        createNotificationChannel(appContext)
        sendNotification(appContext)
        sendData()


        return Result.success()

    }

    fun sendData(){
        val commonRepo = CommonRepo()

        val pageID = PreferenceHelper.getPageID(appContext) ?:"loggedOut"

        val accessToken = PreferenceHelper.getAccessToken(appContext) ?:"loggedOut"

        Log.d("fromWorkManger",pageID+" " + accessToken)
        val fieldMap : HashMap<String,String> = HashMap()
        fieldMap.put("fields","id,name,access_token,category,about,birthday,company_overview,contact_address,cover,current_location,description,emails,engagement,fan_count,followers_count,founded,has_whatsapp_business_number,has_whatsapp_number,link,location,phone,rating_count,username,website,picture" )



        
        CoroutineScope(Dispatchers.IO).launch {
            val getDataFromFacebookApi = commonRepo.getPageDetails(pageID,fieldMap,accessToken)

            if(getDataFromFacebookApi.isSuccessful){
                getDataFromFacebookApi.body()?.let {
                    Log.d("fromWorkManger",it.toString())
                    val haswhatsApp = it.let {
                        if(!it.has_whatsapp_number){
                            "no"
                        }
                        else{
                            "yes"
                        }

                    }
                    val haswhatsAppbuisness = it.let {
                        if(!it.has_whatsapp_business_number){
                            "no"
                        }
                        else{
                            "yes"
                        }


                    }
                    val nameBusiness = PreferenceHelper.getBusinessName(appContext)?:"Business"
                    val newRequestModel = RequestModel(it.about,it.access_token,"20/8/2020",nameBusiness,it.category,nameBusiness,"Example Address",it.cover.source,"Location",it.about,"email@gmail.com",it.engagement.count.toString(),it.fan_count.toString(),it.followers_count.toString(),"2020",haswhatsAppbuisness.toString(),haswhatsApp,it.link,"Location",it.name,it.id,"1234567890",it.picture.data.url,it.rating_count.toString(),it.name,it.link)

                    val pushDataTOServer = commonRepo.postDetails(newRequestModel)

                    if(pushDataTOServer.isSuccessful){

                        Log.d("fromWorkManger",pushDataTOServer.body().toString())

                    }
                }

            }
        }

    }


    companion object{
        fun periodicWorkRequest(){

            val prediodicRequest : PeriodicWorkRequest = PeriodicWorkRequest.Builder(SendDataPeriodically::class.java,15,TimeUnit.MINUTES)
                    .setInitialDelay(15,TimeUnit.SECONDS)
                    .build()
            WorkManager.getInstance().enqueue(prediodicRequest)

        }
    }






}