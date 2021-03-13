package com.ws.zoo.main.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.ws.zoo.R

open class BaseFragment : Fragment() {
    lateinit var mFragmentNavigation: FragmentNavigation
    var progressBar: ContentLoadingProgressBar? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
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
