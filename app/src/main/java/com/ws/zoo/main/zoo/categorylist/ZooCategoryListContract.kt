package com.ws.zoo.main.zoo.categorylist

import com.ws.zoo.data.model.response.zoo.ResultZooDataResponse
import com.ws.zoo.main.base.mvp.IBaseView
import com.ws.zoo.main.base.mvp.IPresenter

interface ZooCategoryListContract {
    interface View : IBaseView {
        /**
         * 成功/失敗取得台北動物園列表資訊
         */
        fun successGetResultData(data: ResultZooDataResponse?)
        fun failGetResultData()

    }

    interface Presenter : IPresenter {
        fun getResultData(scope: String)
    }
}
