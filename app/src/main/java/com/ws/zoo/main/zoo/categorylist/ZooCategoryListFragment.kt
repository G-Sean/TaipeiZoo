package com.ws.zoo.main.zoo.categorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ws.zoo.R
import com.ws.zoo.data.model.response.zoo.ResultZooDataResponse
import com.ws.zoo.data.model.response.zoo.ZooDataModel
import com.ws.zoo.main.base.BaseFragment
import com.ws.zoo.main.view.dialog.CustomDialog
import com.ws.zoo.main.zoo.categorydetail.ZooCategoryDetailFragment
import com.ws.zoo.main.zoo.categorylist.adapter.ZooCategoryListAdapter

class ZooCategoryListFragment : BaseFragment(), View.OnClickListener,
    ZooCategoryListContract.View {
    var rvZooCategory: RecyclerView? = null
    private var tvTitle : TextView? = null
    private var ivMenu :ImageView? = null
    private var mPresenter: ZooCategoryListPresenter? = null
    private var zooCategoryListAdapter: ZooCategoryListAdapter? = null
    private val zooDateList: ArrayList<ZooDataModel> = arrayListOf()
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ZooCategoryListPresenter(this)
        mPresenter?.getResultData("resourceAquire")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zoo_category_list, container, false)?.apply {
            zooCategoryListAdapter = ZooCategoryListAdapter(zooDateList)
            zooCategoryListAdapter?.setOnItemClickListener { adapter, _, position ->
                adapter.getItem(position)?.let { zooInfo ->
                    if (zooInfo is ZooDataModel) {
                        mFragmentNavigation.pushFragment(
                            ZooCategoryDetailFragment.newInstance(
                                zooInfo
                            )
                        )
                    }
                }
            }
            ivMenu = findViewById(R.id.iv_menu)
            ivMenu?.setOnClickListener(this@ZooCategoryListFragment)
            tvTitle = findViewById(R.id.tv_title)

            tvTitle?.text = getString(R.string.zoo_taipei)
            rvZooCategory = findViewById(R.id.recycler_view_zoo_category)
            rvZooCategory?.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = zooCategoryListAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_menu ->mFragmentNavigation.openDrawer()
        }
    }

    override fun successGetResultData(data: ResultZooDataResponse?) {
        data?.let {
            zooDateList.clear()
            it.result?.results?.let { array ->
                zooDateList.addAll(array)
            }
            zooCategoryListAdapter?.notifyDataSetChanged()
        }
    }

    override fun failGetResultData() {
        Toast.makeText(activity, getString(R.string.err_msg), Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        context?.let { context ->
            CustomDialog.let {
                loadingDialog = it.loadingDialog(context)
                loadingDialog?.show()
            }
        }
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

    companion object {
        fun newInstance(): ZooCategoryListFragment {
            val args = Bundle()
            val fragment = ZooCategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}