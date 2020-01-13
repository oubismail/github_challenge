package com.github.toprepo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubApiResult(
    @Json(name = "items")
    val items: List<Item>? = emptyList()
)