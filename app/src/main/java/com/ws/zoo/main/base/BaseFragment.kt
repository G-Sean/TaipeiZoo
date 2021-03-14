package com.ws.zoo.main.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ws.zoo.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {
    lateinit var mFragmentNavigation: FragmentNavigation
    private var progressBar: ContentLoadingProgressBar? = null
    var binding: VB? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>? = null)
        fun popFragment()
        fun openDrawer()
    }

    companion object {
        const val EXTRA_KEY_ZOO_DATA = "extra.key.zoo.data"
        const val EXTRA_KEY_PLANT_DATA = "extra.key.plant.data"
    }
}
