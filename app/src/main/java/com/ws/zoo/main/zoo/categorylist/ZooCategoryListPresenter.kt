package com.ws.zoo.main.zoo.categorylist

import com.ws.zoo.data.repository.ZooRepository
import com.ws.zoo.main.base.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ZooCategoryListPresenter(rootView: ZooCategoryListContract.View?) :
    BasePresenter<ZooCategoryListContract.View?>(rootView), ZooCategoryListContract.Presenter {

    private val zooRepository: ZooRepository by lazy { ZooRepository() }

    override fun getResultData(scope: String) {
        mRootView?.showLoading()
        addCompositeDisposable(zooRepository.getZooData(scope)
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