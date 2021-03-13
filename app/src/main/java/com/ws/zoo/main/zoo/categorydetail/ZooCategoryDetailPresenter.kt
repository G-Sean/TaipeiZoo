package com.ws.zoo.main.zoo.categorydetail

import com.ws.zoo.data.repository.ZooRepository
import com.ws.zoo.main.base.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ZooCategoryDetailPresenter(rootView: ZooCategoryDetailContract.View?) :
    BasePresenter<ZooCategoryDetailContract.View?>(rootView), ZooCategoryDetailContract.Presenter {

    private val zooRepository: ZooRepository by lazy { ZooRepository() }

    override fun getResultData(scope: String, q: String, limit: Int, offset: Int) {
        mRootView?.showLoading()
        addCompositeDisposable(
            zooRepository.getPlantData(scope, q, limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ zooResponse ->
                    mRootView?.successGetResultData(zooResponse)
                    mRootView?.hideLoading()
                }, { error ->
                    error.printStackTrace()
                    mRootView?.failGetResultData()
                    mRootView?.hideLoading()
                })
        )
    }
}