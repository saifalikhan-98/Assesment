package com.khan.assesment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.Wave
import com.khan.assesment.R
import com.khan.assesment.dataRepo.CommonRepo
import com.khan.assesment.databinding.FragmentProfilefragBinding
import com.khan.assesment.models.Result
import com.khan.assesment.roomdb.roomDataBaseClass
import com.khan.assesment.utils.AppUtils
import com.khan.assesment.utils.AppUtils.Companion.isNetworkAvailable
import com.khan.assesment.utils.PreferenceHelper
import com.khan.assesment.utils.Resource
import com.khan.assesment.viewModelFact.GetFBPageDetails
import com.khan.assesment.viewModelFact.PostFBDetailsModelFactory
import com.khan.assesment.viewModels.FbPageDetailsViewModel
import com.khan.assesment.viewModels.PostFBDetails
import com.khan.assesment.viewModels.UserBasicDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Profilefrag : Fragment() {


    private lateinit var binding : FragmentProfilefragBinding
    private val basicDetails : UserBasicDetails by activityViewModels()
    private lateinit var fbPageDetailsViewModel: FbPageDetailsViewModel
    private lateinit var postDataToApi: PostFBDetails


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



        //fillUserProfileData()
        logout()

        return view
    }

    private fun initViewModel() {
        val commonRepo = CommonRepo()
        val fbModelProvider = GetFBPageDetails(commonRepo)
        val postModelProvider = PostFBDetailsModelFactory(commonRepo)
        fbPageDetailsViewModel = ViewModelProvider(this, fbModelProvider).get(FbPageDetailsViewModel::class.java)
        postDataToApi = ViewModelProvider(this, postModelProvider).get(PostFBDetails::class.java)


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

    private fun fillUserProfileData() {

        basicDetails.basicDetails.observe(viewLifecycleOwner, Observer {

            Glide.with(requireContext())
                .load(it.picture.data.url)
                .error(R.drawable.defaultimg)
                .into(binding.profilepic)


            // binding.location.text = it.location.name


        })

        fbPageDetailsViewModel.pageDetails.observe(viewLifecycleOwner, Observer {

            Glide.with(requireContext())
                .load(it.data?.cover)
                .error(R.drawable.defaultimg)
                .into(binding.coverpic)


            // binding.location.text = it.location.name


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