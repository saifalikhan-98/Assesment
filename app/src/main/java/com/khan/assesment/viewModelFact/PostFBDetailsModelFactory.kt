package com.khan.assesment.viewModelFact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.viewModels.PostFBDetails

class PostFBDetailsModelFactory(val commonRepo: CommonRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return  PostFBDetails(commonRepo) as T
    }


}