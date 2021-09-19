package com.khan.assesment.models.PageDetailsModel

data class DataX(
        val access_token: String,
        val category: String,
        val category_list: List<Category>,
        val id: String,
        val name: String,
        val tasks: List<String>
)