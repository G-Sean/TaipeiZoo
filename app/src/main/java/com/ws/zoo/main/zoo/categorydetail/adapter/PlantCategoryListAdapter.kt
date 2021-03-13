package com.ws.zoo.main.zoo.categorydetail.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ws.zoo.R
import com.ws.zoo.data.model.response.plant.PlantDataModel
import com.ws.zoo.data.model.response.zoo.ZooDataModel

class PlantCategoryListAdapter(data: ArrayList<PlantDataModel>?) :
    BaseQuickAdapter<PlantDataModel, BaseViewHolder>(R.layout.widget_vh_plant_category_item, data) {

    override fun convert(holder: BaseViewHolder, item: PlantDataModel) {
        Glide.with(context).load(item.picUrl).into(holder.getView(R.id.iv_pic))
        holder.setText(R.id.tv_name, item.name)
        holder.setText(R.id.tv_info, item.alsoKnown)
    }
}