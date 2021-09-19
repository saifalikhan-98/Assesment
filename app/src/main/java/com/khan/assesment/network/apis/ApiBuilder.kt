package com.khan.assesment.network.apis

import com.khan.assesment.models.PageDetailsModel.PageIdModel
import com.khan.assesment.models.RequestModel
import com.khan.assesment.models.ResponseModel
import com.khan.assesment.models.PageDetailsModel.UserIdModel
import com.khan.assesment.models.ResponseFromFbApi
import retrofit2.Response
import retrofit2.http.*

interface ApiBuilder {

    @POST("fb_page_details")
    suspend fun sendData(@Body requestModel: RequestModel) : Response<ResponseModel>


    @GET("{User_ID}/accounts")
    suspend fun getPageId(@Path("User_ID") User_ID : String,@Query("access_token") access_token : String) : Response<PageIdModel>


    @GET("me")
    suspend fun getUserId(@Query("access_token") access_token : String) : Response<UserIdModel>


    @GET("{PAGE_ID}")
    suspend fun getFbPageDetails(@Path("PAGE_ID") PAGE_ID : String,@QueryMap fields : HashMap<String, String>,@Query("access_token") access_token : String) : Response<ResponseFromFbApi>

}