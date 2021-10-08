package com.khan.assesment.presentation.ui.loginfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.gson.GsonBuilder
import com.khan.assesment.models.BasicDetailsResponse
import com.khan.assesment.R
import com.khan.assesment.utils.PreferenceHelper
import com.khan.assesment.viewmodels.fbdetailsviewmodels.UserBasicDetails
import com.khan.assesment.databinding.FragmentHomeBinding
import com.khan.assesment.presentation.ui.MainActivity
import com.khan.assesment.utils.AppUtils
import com.khan.assesment.utils.AppUtils.Companion.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*


@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFrag : Fragment(),FacebookCallback<LoginResult> {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var callBackManager : CallbackManager
    private lateinit var mActivity : MainActivity
    private val basicViewModel : UserBasicDetails by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity
        if(!isNetworkAvailable(requireContext())){

            AppUtils.showToastMsg("Please make sure to have active internet connection",requireContext())
        }

    }

    override fun onResume() {
        super.onResume()
        if(!isNetworkAvailable(requireContext())){

            AppUtils.showToastMsg("Please make sure to have active internet connection",requireContext())
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_home,container,false)

        if(isNetworkAvailable(requireContext())){
            initFaceBookLoginSetup(view)


        }else{
            AppUtils.showToastMsg("Please make sure to have active internet connection",requireContext())
        }






        return view
    }


    private fun initFaceBookLoginSetup(view: View) {
        callBackManager = CallbackManager.Factory.create()
        val login = view.findViewById<LoginButton>(R.id.login_button)
        login.fragment=this
        login.setPermissions(Arrays.asList("email","pages_read_engagement","pages_read_user_content","business_management","pages_manage_metadata"))
        login.registerCallback(callBackManager, this)

        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = AppUtils.checkLoginStatus()

        if(isLoggedIn){
            navigateToProfile()
            getUsersData(accessToken)
        }
    }

    override fun onSuccess(result: LoginResult?) {
        result?.let{
            Log.d("FacebookLogin", "Login Successful")
            val accessToken = result.accessToken
            getUsersData(accessToken)
            PreferenceHelper.setUserStatus(requireContext(),true)

            navigateToProfile()
        }

    }



    private fun getUsersData(accessToken: AccessToken) {
        val bundle = Bundle()

        Log.d("accessToken", accessToken.token)
        PreferenceHelper.setAccessToken(requireContext(), accessToken.token)
        val graphRequest = GraphRequest.newMeRequest(
                accessToken,
                object : GraphRequest.GraphJSONObjectCallback {
                    override fun onCompleted(jsonobject: JSONObject?, response: GraphResponse?) {
                        jsonobject?.let {

                            Log.d("FaceBookData", it.toString())
                            val gsonBuilder = GsonBuilder()
                            val gson = gsonBuilder.create()
                            val jsonObj = gson.fromJson(
                                    it.toString(),
                                    BasicDetailsResponse::class.java
                            )

                            sendDataToViewModel(jsonObj)

                        }

                    }
                })
        bundle.putString("fields","id,name,email,picture,link")



        graphRequest.parameters = bundle

        graphRequest.executeAsync()
    }

    private fun sendDataToViewModel(jsonObj: BasicDetailsResponse) {

        basicViewModel.getUserBasicDetails(jsonObj)

    }

    override fun onCancel() {
        Log.d("FacebookLogin", "Canceled")
    }

    override fun onError(error: FacebookException?) {

        Log.d("FacebookLogin", error.toString())
    }
/*

    val regiterForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        callBackManager.onActivityResult(FaceBookResquestCode, it.resultCode, it.data)

    }
*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun navigateToProfile(){
        findNavController().navigate(R.id.action_homeFrag_to_profilefrag)
    }
}