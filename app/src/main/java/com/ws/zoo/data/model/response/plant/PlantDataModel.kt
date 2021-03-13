package com.ws.zoo.data.model.response.plant

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlantDataModel(
    @field:SerializedName("F_Pic01_URL")
    val picUrl: String? = null,

    @field:SerializedName("F_AlsoKnown")
    val alsoKnown: String? = null,

    @field:SerializedName("\uFEFFF_Name_Ch")
    val name: String? = null,

    @field:SerializedName("F_Brief")
    val brief: String? = null,

    @field:SerializedName("F_Feature")
    val feature: String? = null,

    @field:SerializedName("F_Function＆Application")
    val function: String? = null,

    @field:SerializedName("F_Update")
    val update: String? = null
) : Serializable
