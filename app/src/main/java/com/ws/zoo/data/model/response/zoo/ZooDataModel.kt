package com.ws.zoo.data.model.response.zoo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ZooDataModel(
    @field:SerializedName("E_Pic_URL")
    val picUrl: String? = null,

    @field:SerializedName("E_Geo")
    val geo: String? = null,

    @field:SerializedName("E_Info")
    val info: String? = null,
    @field:SerializedName("E_no")
    val no: String? = null,

    @field:SerializedName("E_Category")
    val category: String? = null,

    @field:SerializedName("E_Name")
    val name: String? = null,

    @field:SerializedName("E_Memo")
    val memo: String? = null,

    @field:SerializedName("_id")
    val id: Int? = null,

    @field:SerializedName("E_URL")
    val url: String? = null
) : Serializable
