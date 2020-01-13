package com.github.toprepo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(

    @Json(name = "description")
    val description: String? = "",
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "owner")
    val owner: Owner,
    @Json(name = "stargazers_count")
    val stargazersCount: Int? = 0

)