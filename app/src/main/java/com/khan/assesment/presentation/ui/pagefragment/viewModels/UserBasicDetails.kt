package com.khan.assesment.presentation.ui.pagefragment.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khan.assesment.models.BasicDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserBasicDetails @Inject constructor(): ViewModel() {

    val basicDetails : MutableLiveData<BasicDetailsResponse> = MutableLiveData()


    fun getUserBasicDetails(basicDetailsResponse: BasicDetailsResponse){

        basicDetails.postValue(basicDetailsResponse)

    }


}