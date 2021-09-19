package com.khan.assesment.models

data class Data(
    val height: Int,
    val is_silhouette: Boolean,
    val url: String,
    val width: Int
){
    val imageUrl
    get() = url
}