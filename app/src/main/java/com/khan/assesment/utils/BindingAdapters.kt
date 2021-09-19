package com.khan.assesment.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.khan.assesment.R
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imageUrl")
fun loadImage(view : CircleImageView, imageUrl:String?){

    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
                .load(imageUrl)
                .error(R.drawable.defaultimg)
                .into(view)
    }else{
        view.setImageResource(R.drawable.defaultimg)
    }


}
