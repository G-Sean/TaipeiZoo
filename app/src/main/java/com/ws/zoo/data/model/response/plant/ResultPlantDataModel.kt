package com.ws.zoo.data.model.response.plant

import com.google.gson.annotations.SerializedName

data class ResultPlantDataModel(
    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("offset")
    val offset: Int? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("sort")
    val sort: String? = null,

    @field:SerializedName("results")
    val results: ArrayList<PlantDataModel>? = null
)


