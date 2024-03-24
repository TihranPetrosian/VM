package com.example.vm.recycler

import com.google.gson.annotations.SerializedName

data class DogsItem(
    @SerializedName("bred_for")
    val bred_for: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("reference_image_id")
    val reference_image_id: String?
)