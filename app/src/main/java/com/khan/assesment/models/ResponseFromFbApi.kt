package com.khan.assesment.models

data class ResponseFromFbApi(
    val about: String,
    val access_token: String,
    val category: String,
    val cover: Cover,
    val engagement: Engagement,
    val fan_count: Int,
    val followers_count: Int,
    val has_whatsapp_business_number: Boolean,
    val has_whatsapp_number: Boolean,
    val id: String,
    val link: String,
    val name: String,
    val picture: PictureX,
    val rating_count: Int
)