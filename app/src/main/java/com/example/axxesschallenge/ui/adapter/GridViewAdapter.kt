package com.example.axxesschallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.axxesschallenge.BR
import com.example.axxesschallenge.R
import com.example.axxesschallenge.databinding.GridItemAxxessBinding
import com.example.axxesschallenge.model.ImageResponse

class GridViewAdapter(private val imgList: List<ImageResponse>) :
    BaseAdapter() {

    override fun getView(position: Int, p1: View?, p2: ViewGroup): View {
        val img = this.imgList[position]

        val inflater = LayoutInflater.from(p2.context)
        val dataBinding: GridItemAxxessBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.grid_item_axxess, p2, false
        )

        dataBinding.setVariable(
            BR.imageResponse,
            img
        ) //BR - generated class; BR.imageResponse - 'imageResponse' is a variable name declared in layout

        dataBinding.executePendingBindings()
        return dataBinding.root
    }

    override fun getItem(position: Int): Any {
        return imgList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imgList.size
    }
}