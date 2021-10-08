package com.khan.assesment.presentation.ui.pagefragment.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.facebook.login.LoginManager
import com.khan.assesment.databinding.FragmentProfilefragBinding
import com.khan.assesment.models.Result
import com.khan.assesment.roomdb.roomDataBaseClass
import com.khan.assesment.utils.AppUtils
import com.khan.assesment.utils.AppUtils.Companion.isNetworkAvailable
import com.khan.assesment.utils.PreferenceHelper
import com.khan.assesment.network.Resource
import com.khan.assesment.viewmodels.fbdetailsviewmodels.FbPageDetailsViewModel
import com.khan.assesment.viewmodels.fbdetailsviewmodels.PostFBDetails
import com.khan.assesment.viewmodels.fbdetailsviewmodels.UserBasicDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Profilefrag : Fragment() {


    private lateinit var binding : FragmentProfilefragBinding
    private val basicDetails : UserBasicDetails by activityViewModels()
    private val fbPageDetailsViewModel: FbPageDetailsViewModel by activityViewModels()
    private val postDataToApi: PostFBDetails by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = FragmentProfilefragBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.basic = basicDetails

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding.page = fbPageDetailsViewModel


        val view = binding.root




        logout()

        return view
    }

    private fun initViewModel() {

        if(!isNetworkAvailable(requireContext())){
            CoroutineScope(Dispatchers.IO).launch {
                getDataFromRoom(requireContext())
            }


        }else{
            fbPageDetailsViewModel.getUserId(requireContext())
            CoroutineScope(Dispatchers.IO).launch {
                getDataFromRoom(requireContext())
            }

        }



        makePostApiCall()



    }

    private fun logout() {
        binding.facebookBtn.setOnClickListener {
            LoginManager.getInstance().logOut()
            PreferenceHelper.setUserStatus(requireContext(),false)
            findNavController().popBackStack()
        }

    }

    private fun makePostApiCall() {
        val dialog = AppUtils.showProgress(requireActivity())
        dialog.show()
        fbPageDetailsViewModel.pageDetails.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                        dialog.dismiss()
                        postDataToApi.postFbDetails(it, requireContext())

                    }

                }
                is Resource.Error -> {
                    dialog.dismiss()
                    AppUtils.showToastMsg("Error loading data from facebook", requireContext())

                }

            }
        })
    }



    fun getDataFromRoom(context: Context){

        var savedApiResponse : Result
        try{
            val db = roomDataBaseClass.getDatabaseInstance(context)
            val resApiDao =  db.responseDao()
            savedApiResponse = resApiDao.getResponse()


            Log.d("xRoomGetResponse", savedApiResponse.toString())

        }catch (e: Exception){
            Log.d("xRoom", "error ${e.localizedMessage}")

        }
    }


}