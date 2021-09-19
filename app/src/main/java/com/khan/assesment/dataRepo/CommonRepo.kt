package com.khan.assesment.dataRepo

import com.khan.assesment.models.RequestModel
import com.khan.assesment.utils.RetroFitInstance
import com.khan.assesment.utils.RetrofitForFaceBook

class CommonRepo {

    suspend fun postDetails(requestModel: RequestModel) = RetroFitInstance.api.sendData(requestModel)

    suspend fun getUserId(access_token : String) = RetrofitForFaceBook.api.getUserId(access_token)

    suspend fun getPageId(userid:String, access_token: String) = RetrofitForFaceBook.api.getPageId(userid,access_token)

    suspend fun getPageDetails(page_id:String, fieldsMap : HashMap<String,String>,access_token: String) = RetrofitForFaceBook.api.getFbPageDetails(page_id,fieldsMap,access_token)



}