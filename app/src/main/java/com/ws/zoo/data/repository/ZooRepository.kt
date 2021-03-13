package com.ws.zoo.data.repository


import com.ws.zoo.data.model.response.plant.ResultPlantDataResponse
import com.ws.zoo.data.model.response.zoo.ResultZooDataResponse
import com.ws.zoo.data.network.RetrofitManager
import io.reactivex.Single
import io.reactivex.internal.operators.flowable.FlowableLimit

class ZooRepository {
    fun getZooData(scope: String): Single<ResultZooDataResponse> {
        return RetrofitManager.zooApiService.getZooData(scope = scope)
    }

    fun getPlantData(
        scope: String,
        q: String,
        limit: Int,
        offset: Int
    ): Single<ResultPlantDataResponse> {
        return RetrofitManager.zooApiService.getPlantData(
            scope = scope,
            q = q,
            limit = limit,
            offset = offset
        )
    }
}