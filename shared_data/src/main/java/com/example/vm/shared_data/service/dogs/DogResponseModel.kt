package com.example.vm.shared_data.service.dogs

import com.google.gson.annotations.SerializedName

data class DogResponseModel(
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("reference_image_id")
    val referenceImageId: String?,
)
