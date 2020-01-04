package com.example.td_2

import com.squareup.moshi.Json

//data class Task(val id: String, val title: String , val description: String = "Description content") {


data class Task(
    @field:Json(name = "id")
    val id: String? = null,
    @field:Json(name = "description")
    val description: String = "description",
    @field:Json(name = "title")
    val title: String = "Description content"
)