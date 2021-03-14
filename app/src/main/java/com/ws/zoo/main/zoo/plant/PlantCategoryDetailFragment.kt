package com.ws.zoo.main.zoo.plant

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.ws.zoo.R
import com.ws.zoo.data.model.response.plant.PlantDataModel
import com.ws.zoo.databinding.FragmentPlantCategoryDetailBinding
import com.ws.zoo.main.base.BaseFragment

class PlantCategoryDetailFragment :
    BaseFragment<FragmentPlantCategoryDetailBinding>(FragmentPlantCategoryDetailBinding::inflate),
    View.OnClickListener {

    private var plantData: PlantDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            plantData = it.getSerializable(EXTRA_KEY_PLANT_DATA) as PlantDataModel?
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            plantData?.let { data ->
                context?.let { context ->
                    ivPic.let { iv ->
                        Glide.with(context).load(data.picUrl).into(iv)
                    }
                }
                ivBack.setOnClickListener(this@PlantCategoryDetailFragment)
                tvName.text = data.name
                tvAlsoKnown.text = data.alsoKnown
                tvBrief.text = data.brief
                tvFeature.text = data.feature
                tvFunction.text = data.function
                tvUpdate.text = String.format(getString(R.string.plants_last_update, data.update))
                tvTitle.text = data.name
            }
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
            R.id.iv_back -> {
                mFragmentNavigation.popFragment()
            }
        }
    }
}

