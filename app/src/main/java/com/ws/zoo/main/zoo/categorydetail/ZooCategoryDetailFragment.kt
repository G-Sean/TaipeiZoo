package com.ws.zoo.main.zoo.categorydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.ws.zoo.R
import com.ws.zoo.data.model.response.plant.PlantDataModel
import com.ws.zoo.data.model.response.plant.ResultPlantDataResponse
import com.ws.zoo.data.model.response.zoo.ZooDataModel
import com.ws.zoo.main.base.BaseFragment
import com.ws.zoo.main.zoo.categorydetail.adapter.PlantCategoryListAdapter
import com.ws.zoo.main.zoo.plant.PlantCategoryDetailFragment
import org.w3c.dom.Text

class ZooCategoryDetailFragment : BaseFragment(), View.OnClickListener,
    ZooCategoryDetailContract.View {
    private var ivPic: ImageView? = null
    private var tvInfo: TextView? = null
    private var tvMemo: TextView? = null
    private var tvCategory: TextView? = null
    private var tvWebOpen: TextView? = null
    private var frameLayout: FrameLayout? = null
    private var rvPlantCategory: RecyclerView? = null
    private var ivBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var rlEventView: RelativeLayout? = null

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zoo_category_detail, container, false)?.apply {
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

            ivPic = findViewById(R.id.iv_pic)
            tvInfo = findViewById(R.id.tv_info)
            tvMemo = findViewById(R.id.tv_memo)
            tvCategory = findViewById(R.id.tv_category)
            rlEventView = findViewById(R.id.rl_event_view)
            rlEventView?.setOnClickListener(this@ZooCategoryDetailFragment)
            tvWebOpen = findViewById(R.id.tv_web_open)
            tvWebOpen?.setOnClickListener(this@ZooCategoryDetailFragment)

            frameLayout = findViewById(R.id.bottom_sheet)
            ivBack = findViewById(R.id.iv_back)
            ivBack?.setOnClickListener(this@ZooCategoryDetailFragment)

            tvTitle = findViewById(R.id.tv_title)
            rvPlantCategory = findViewById(R.id.recycler_view_plant_category)

            rvPlantCategory?.apply {
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
            frameLayout?.let {
                bottomSheetBehavior = BottomSheetBehavior.from(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zooData?.let { data ->
            context?.let { context ->
                ivPic?.let { iv ->
                    Glide.with(context).load(data.picUrl).into(iv)
                }
            }
            tvTitle?.text = data.name
            tvInfo?.text = data.info
            tvMemo?.text = if (TextUtils.isEmpty(data.memo)) {
                context?.getString(R.string.default_memo)
            } else {
                data.memo
            }
            tvCategory?.text = data.category
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_web_open -> {
                zooData?.let {
                    val intent: Intent = Intent()
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

