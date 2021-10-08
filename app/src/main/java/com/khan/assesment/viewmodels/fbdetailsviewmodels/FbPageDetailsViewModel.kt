package com.khan.assesment.viewmodels.fbdetailsviewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.models.PageDetailsModel.PageIdModel
import com.khan.assesment.models.PageDetailsModel.UserIdModel
import com.khan.assesment.models.RequestModel
import com.khan.assesment.models.ResponseFromFbApi
import com.khan.assesment.utils.PreferenceHelper
import com.khan.assesment.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FbPageDetailsViewModel @Inject constructor(val commonRepo: CommonRepo) : ViewModel() {



    val pageDetails : MutableLiveData<Resource<RequestModel>> = MutableLiveData()
    val accountDetail : MutableLiveData<Resource<PageIdModel>> = MutableLiveData()


    private lateinit var userId : String
    private lateinit var access_token : String
    private lateinit var page_Id : String
    private lateinit var nameBuisness : String

    fun getUserId(context : Context){

        access_token = PreferenceHelper.getAccessToken(context)?:"UserLoggedOut"
        CoroutineScope(Dispatchers.IO).launch {
            val response : Response<UserIdModel> = commonRepo.getUserId(access_token)
            HandleUserIdResponse(response,context)

        }

    }

    private fun HandleUserIdResponse(response: Response<UserIdModel>, context: Context) {

        if(response.isSuccessful){
            response.body()?.let{
                userId = it.id
                PreferenceHelper.setUserId(context,userId)
            }
            CoroutineScope(Dispatchers.IO).launch {
                accountDetail.postValue(Resource.Loading())
                val getPostId = commonRepo.getPageId(userId,access_token)
                HandleResponse(getPostId,context)
            }
        }


    }

    private fun HandleResponse(response: Response<PageIdModel>, context: Context){

        if(response.isSuccessful){
            response.body()?.let{
                page_Id = it.data.get(0).id
                PreferenceHelper.setPageID(context,page_Id)
                nameBuisness = it.data.get(0).name
                PreferenceHelper.setBusinessName(context,nameBuisness)
                accountDetail.postValue(Resource.Success(it))
                CoroutineScope(Dispatchers.IO).launch {
                    val fieldMap : HashMap<String,String> = HashMap()
                    fieldMap.put("fields","id,name,access_token,category,about,birthday,company_overview,contact_address,cover,current_location,description,emails,engagement,fan_count,followers_count,founded,has_whatsapp_business_number,has_whatsapp_number,link,location,phone,rating_count,username,website,picture" )
                    pageDetails.postValue(Resource.Loading())
                    val getPostId = commonRepo.getPageDetails(page_Id, fieldMap,access_token )
                    pageDetails.postValue(HandleGetPageDetail(getPostId))
                }
            }

        }else{
            accountDetail.postValue(Resource.Error(response.message()))
        }


    }

    private fun HandleGetPageDetail(response: Response<ResponseFromFbApi>): Resource<RequestModel>? {
        if(response.isSuccessful){
            response.body()?.let{
                Log.d("xResponseFromFacebook",it.toString())
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

                val newRequestModel = RequestModel(it.about,it.access_token,"20/8/2020",nameBuisness,it.category,nameBuisness,"Example Address",it.cover.source,"Location",it.about,"email@gmail.com",it.engagement.count.toString(),it.fan_count.toString(),it.followers_count.toString(),"2020",haswhatsAppbuisness.toString(),haswhatsApp,it.link,"Location",it.name,it.id,"1234567890",it.picture.data.url,it.rating_count.toString(),it.name,it.link)

                return Resource.Success(newRequestModel)

            }

        }
        return Resource.Error(response.message())


    }






}