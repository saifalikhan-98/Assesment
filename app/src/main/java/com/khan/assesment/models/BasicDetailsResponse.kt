package com.khan.assesment.models

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khan.assesment.R
import de.hdodenhof.circleimageview.CircleImageView

data class BasicDetailsResponse(
        val birthday: String,
        val email: String,
        val id: String,
        val location: Location,
        val name: String,
        val picture: Picture
){


}