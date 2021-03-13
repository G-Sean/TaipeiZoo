package com.ws.zoo.main.zoo.plant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ws.zoo.R
import com.ws.zoo.data.model.response.plant.PlantDataModel
import com.ws.zoo.main.base.BaseFragment

class PlantCategoryDetailFragment : BaseFragment(),View.OnClickListener{
    private var ivPic: ImageView? = null
    private var tvName: TextView? = null
    private var tvAlsoKnown: TextView? = null
    private var tvBrief: TextView? = null
    private var tvFeature: TextView? = null
    private var tvFunction: TextView? = null
    private var tvUpdate: TextView? = null
    private var ivBack :ImageView? = null
    private var tvTitle :TextView? = null

    private var plantData: PlantDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            plantData = it.getSerializable(EXTRA_KEY_PLANT_DATA) as PlantDataModel?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_category_detail, container, false)?.apply {
            ivPic = findViewById(R.id.iv_pic)
            tvName = findViewById(R.id.tv_name)
            tvAlsoKnown = findViewById(R.id.tv_also_known)
            tvBrief = findViewById(R.id.tv_brief)
            tvFeature = findViewById(R.id.tv_feature)
            tvFunction = findViewById(R.id.tv_function)
            tvUpdate = findViewById(R.id.tv_update)
            ivBack = findViewById(R.id.iv_back)
            tvTitle = findViewById(R.id.tv_title)

            ivBack?.setOnClickListener(this@PlantCategoryDetailFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plantData?.let { data ->
            context?.let { context ->
                ivPic?.let { iv ->
                    Glide.with(context).load(data.picUrl).into(iv)
                }
            }
            tvName?.text = data.name
            tvAlsoKnown?.text = data.alsoKnown
            tvBrief?.text = data.brief
            tvFeature?.text = data.feature
            tvFunction?.text = data.function
            tvUpdate?.text = String.format(getString(R.string.plants_last_update,data.update))
            tvTitle?.text =data.name
        }
    }

    companion object {
        fun newInstance(plantData: PlantDataModel): PlantCategoryDetailFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_KEY_PLANT_DATA, plantData)
            val fragment = PlantCategoryDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back ->{
                mFragmentNavigation.popFragment()
            }
        }
    }
}

