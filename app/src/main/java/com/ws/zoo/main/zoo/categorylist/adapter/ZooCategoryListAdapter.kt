package com.ws.zoo.main.zoo.categorylist.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ws.zoo.R
import com.ws.zoo.data.model.response.zoo.ZooDataModel

class ZooCategoryListAdapter(data: ArrayList<ZooDataModel>?) :
    BaseQuickAdapter<ZooDataModel, BaseViewHolder>(R.layout.widget_vh_zoo_category_item, data) {

    override fun convert(holder: BaseViewHolder, item: ZooDataModel) {
        Glide.with(context).load(item.picUrl).into(holder.getView(R.id.iv_pic))
        holder.setText(R.id.tv_name, item.name)
        holder.setText(R.id.tv_info, item.info)
        holder.setText(
            R.id.tv_memo, if (TextUtils.isEmpty(item.memo)) {
                context.getString(R.string.default_memo)
            } else {
                item.memo
            }
        )
    }
}