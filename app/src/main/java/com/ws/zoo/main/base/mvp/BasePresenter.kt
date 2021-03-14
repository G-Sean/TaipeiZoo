package com.ws.zoo.main.base.mvp

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V>(rootView: V?) : IPresenter {
    var mRootView: V? = rootView
    private var mContext: Context? = null
    private var compositeDisposable: CompositeDisposable? = null

    fun addCompositeDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    init {
        mContext = when (mRootView) {
            is Activity -> {
                mRootView as Context?
            }
            is Fragment -> {
                (mRootView as Fragment).activity
            }
            else -> {
                (mRootView as View).context
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
    }
}