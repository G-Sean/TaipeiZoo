package com.ws.zoo.data.model.response.zoo

import com.google.gson.annotations.SerializedName

data class ResultZooDataResponse(
    @field:SerializedName("result")
    val result: ResultZooDataModel? = null
)
