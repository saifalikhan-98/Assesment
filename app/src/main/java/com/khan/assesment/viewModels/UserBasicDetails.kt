package com.khan.assesment.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khan.assesment.models.BasicDetailsResponse

class UserBasicDetails: ViewModel() {

    val basicDetails : MutableLiveData<BasicDetailsResponse> = MutableLiveData()


    fun getUserBasicDetails(basicDetailsResponse: BasicDetailsResponse){

        basicDetails.postValue(basicDetailsResponse)

    }


}