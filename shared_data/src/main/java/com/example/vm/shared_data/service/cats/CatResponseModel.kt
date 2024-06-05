package com.example.vm.shared_data.service.cats

import com.google.gson.annotations.SerializedName

data class CatResponseModel(
    @SerializedName("bred_for")
    val bredFor: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("reference_image_id")
    val referenceImageId: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("description")
    val description: String?,
)
