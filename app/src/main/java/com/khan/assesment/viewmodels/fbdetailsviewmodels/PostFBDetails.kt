package com.khan.assesment.viewmodels.fbdetailsviewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.models.RequestModel
import com.khan.assesment.models.ResponseModel
import com.khan.assesment.models.Result
import com.khan.assesment.roomdb.ResponseDao
import com.khan.assesment.roomdb.roomDataBaseClass
import com.khan.assesment.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PostFBDetails @Inject constructor(val commonRepo: CommonRepo) : ViewModel() {


        val profileDetails : MutableLiveData<Resource<ResponseModel>> = MutableLiveData()

        private lateinit var resApiDao : ResponseDao


        fun postFbDetails(requestModel : RequestModel,context: Context)=viewModelScope.launch {

            try{

                profileDetails.postValue(Resource.Loading())

                val db = roomDataBaseClass.getDatabaseInstance(context)
                resApiDao =  db.responseDao()

                val response = commonRepo.postDetails(requestModel)

                profileDetails.postValue(HandleResponse(response))

            }catch ( e : Exception){

                profileDetails.postValue(Resource.Error("Server timeout"))
            }



        }

    private fun HandleResponse(response: Response<ResponseModel>): Resource<ResponseModel>? {

        if(response.isSuccessful){
            response.body()?.let {
                Log.d("xResponseApi",it.result.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    setDataForRoom(it.result.get(0))
                }


                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun setDataForRoom(result : Result) {
        CoroutineScope(Dispatchers.IO).launch{
            try{

                val rowId = resApiDao.saveResponse(result)
                Log.d("xRoomSetResponse", rowId.toString())

            }catch (e : Exception){

                Log.d("xRoom","error ${e.localizedMessage}")
            }
        }


    }


}