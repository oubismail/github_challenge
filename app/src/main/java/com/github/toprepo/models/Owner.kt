package com.github.toprepo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "avatar_url")
    val avatarUrl: String? = "",
    @Json(name = "login")
    val login: String? = ""
)