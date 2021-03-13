package com.ws.zoo.main.zoo.categorydetail

import com.ws.zoo.data.model.response.plant.ResultPlantDataResponse
import com.ws.zoo.main.base.mvp.IBaseView
import com.ws.zoo.main.base.mvp.IPresenter


interface ZooCategoryDetailContract {
    interface View : IBaseView {
        /**
         * 成功/失敗取得台北植物園列表資訊
         */
        fun successGetResultData(data: ResultPlantDataResponse?)
        fun failGetResultData()

    }

    interface Presenter : IPresenter {
        fun getResultData(scope: String, q: String, limit: Int, offset: Int)
    }
}
