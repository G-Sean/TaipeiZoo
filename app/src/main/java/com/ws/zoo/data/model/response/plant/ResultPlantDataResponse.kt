package com.ws.zoo.data.model.response.plant

import com.google.gson.annotations.SerializedName

data class ResultPlantDataResponse(
    @field:SerializedName("result")
    val result: ResultPlantDataModel? = null
)
