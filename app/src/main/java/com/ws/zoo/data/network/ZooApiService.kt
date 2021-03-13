package com.ws.zoo.data.network

import com.ws.zoo.data.model.response.plant.ResultPlantDataResponse
import com.ws.zoo.data.model.response.zoo.ResultZooDataResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ZooApiService {
    @GET("/api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    fun getZooData(@Query("scope") scope: String?): Single<ResultZooDataResponse>

    @GET("/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14")
    fun getPlantData(
        @Query("scope") scope: String?,
        @Query("q") q: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Single<ResultPlantDataResponse>
}
