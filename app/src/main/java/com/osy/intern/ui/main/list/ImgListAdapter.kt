package com.osy.intern.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osy.intern.data.vo.ImgVO
import com.osy.intern.databinding.ItemImgBinding
import kotlinx.android.synthetic.main.item_img.view.*

class ImgListAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<ImgVO.Document, ImgViewHolder>(
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
            ItemImgBinding.inflate(LayoutInflater.from(parent.context), parent, false), lifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val document = getItem(position)
        Glide.with(holder.itemView.img).load(document.imageUrl).into(holder.itemView.img)
    }

}
class ImgViewHolder(binding: ViewDataBinding, lifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}
