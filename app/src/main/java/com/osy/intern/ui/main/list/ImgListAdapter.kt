package com.osy.intern.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osy.intern.data.vo.ImgVO
import com.osy.intern.databinding.ItemImgBinding

class ImgListAdapter : ListAdapter<ImgVO.Document, ImgListAdapter.ImgViewHolder>(
    object : DiffUtil.ItemCallback<ImgVO.Document>() {
        override fun areItemsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        return ImgViewHolder(
            ItemImgBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ).root
        )
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {

    }

    class ImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}