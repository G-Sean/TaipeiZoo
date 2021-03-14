package com.ws.zoo.main.zoo.categorydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.ws.zoo.R
import com.ws.zoo.data.model.response.plant.PlantDataModel
import com.ws.zoo.data.model.response.plant.ResultPlantDataResponse
import com.ws.zoo.data.model.response.zoo.ZooDataModel
import com.ws.zoo.databinding.FragmentZooCategoryDetailBinding
import com.ws.zoo.main.base.BaseFragment
import com.ws.zoo.main.zoo.categorydetail.adapter.PlantCategoryListAdapter
import com.ws.zoo.main.zoo.plant.PlantCategoryDetailFragment
import kotlinx.android.synthetic.main.dialog_bottom_sheet_view.*

class ZooCategoryDetailFragment :
    BaseFragment<FragmentZooCategoryDetailBinding>(FragmentZooCategoryDetailBinding::inflate),
    View.OnClickListener,
    ZooCategoryDetailContract.View {

    private var plantCategoryListAdapter: PlantCategoryListAdapter? = null
    private val plantDateList: ArrayList<PlantDataModel> = arrayListOf()

    private var zooData: ZooDataModel? = null

    private var mPresenter: ZooCategoryDetailPresenter? = null
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var limit: Int = 10//每次拿取的數量
    private var offset: Int = 0//從第幾個開始拿取

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        args?.let {
            zooData = it.getSerializable(EXTRA_KEY_ZOO_DATA) as ZooDataModel?
        }

        mPresenter = ZooCategoryDetailPresenter(this)
        zooData?.let {
            it.name?.let { name ->
                mPresenter?.getResultData("resourceAquire", name, limit, 0)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            plantCategoryListAdapter = PlantCategoryListAdapter(plantDateList)
            plantCategoryListAdapter?.setOnItemClickListener { adapter, _, position ->
                adapter.getItem(position)?.let { plantInfo ->
                    if (plantInfo is PlantDataModel) {
                        mFragmentNavigation.pushFragment(
                            PlantCategoryDetailFragment.newInstance(
                                plantInfo
                            )
                        )
                    }
                }
            }

            rl_event_view?.setOnClickListener(this@ZooCategoryDetailFragment)
            tvWebOpen.setOnClickListener(this@ZooCategoryDetailFragment)
            ivBack.setOnClickListener(this@ZooCategoryDetailFragment)

            recycler_view_plant_category?.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = plantCategoryListAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        val lastPosition =
                            (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val nearTop = (lastPosition + 1) == recyclerView.adapter?.itemCount
                        if (nearTop) {
                            zooData?.let {
                                it.name?.let { name ->
                                    mPresenter?.getResultData("resourceAquire", name, limit, offset)
                                }
                            }
                        }
                    }
                })
            }

            //init the bottom sheet view
            bottom_sheet?.let {
                bottomSheetBehavior = BottomSheetBehavior.from(it)
            }

            zooData?.let { data ->
                context?.let { context ->
                    ivPic.let { iv ->
                        Glide.with(context).load(data.picUrl).into(iv)
                    }
                }
                tvTitle.text = data.name
                tvInfo.text = data.info
                tvMemo.text = if (TextUtils.isEmpty(data.memo)) {
                    context?.getString(R.string.default_memo)
                } else {
                    data.memo
                }
                tvCategory.text = data.category
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_web_open -> {
                zooData?.let {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(it.url)
                    startActivity(intent)
                }
            }
            R.id.iv_back -> {
                mFragmentNavigation.popFragment()
            }
            R.id.rl_event_view -> {
                bottomSheetBehavior?.state = STATE_EXPANDED
            }
        }
    }

    override fun successGetResultData(data: ResultPlantDataResponse?) {
        data?.let {
            val conunt = it.result?.count ?: 0
            if (conunt == 0) {
                plantDateList.clear()
                offset = 0
            }

            if (offset < conunt) {
                offset += limit
            }

            it.result?.results?.let { array ->
                plantDateList.addAll(array)
            }

            plantCategoryListAdapter?.notifyDataSetChanged()
        }
    }

    override fun failGetResultData() {
        Toast.makeText(activity, getString(R.string.err_msg), Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    companion object {
        fun newInstance(zooData: ZooDataModel): ZooCategoryDetailFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_KEY_ZOO_DATA, zooData)
            val fragment = ZooCategoryDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

