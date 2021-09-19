package com.khan.assesment.models.PageDetailsModel

data class FbApiResponse(
    val about: String,
    val access_token: String,
    val followers_count: Int,
    val id: String,
    val link: String,
    val name: String
)