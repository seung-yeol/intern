package com.osy.intern.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.osy.intern.R
import com.osy.intern.data.vo.ImgVO
import com.osy.intern.databinding.ItemImgBinding
import com.osy.intern.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_img.view.*
import javax.inject.Inject

class ImgListAdapter @Inject constructor(private val activity: AppCompatActivity) :
    ListPreloader.PreloadSizeProvider<ImgVO.Document>, ListPreloader.PreloadModelProvider<ImgVO.Document>, ListAdapter<ImgVO.Document, ImgViewHolder>(
    object : DiffUtil.ItemCallback<ImgVO.Document>() {
        override fun areItemsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean =
            oldItem == newItem
    }
) {
    //1.미리 이미지 읽어올 아이템들
    override fun getPreloadItems(position: Int): MutableList<ImgVO.Document> {
        return mutableListOf(getItem(position))
    }

    //2.미리 읽어올 이미지 사이즈
    override fun getPreloadSize(item: ImgVO.Document, adapterPosition: Int, perItemPosition: Int): IntArray? {
        return intArrayOf(itemWidth, (item.height.toDouble() / item.width * itemWidth).toInt())
    }

    //3.미리 읽어올 이미지 로드
    override fun getPreloadRequestBuilder(item: ImgVO.Document): RequestBuilder<*>? {
        return Glide.with(activity).load(item.imageUrl)
    }

    private var itemWidth = 0   //가로세로 이미지 크기비율을 맞추기위해서 구함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        itemWidth = parent.measuredWidth / 2 - 6
        return ImgViewHolder(
            ItemImgBinding.inflate(LayoutInflater.from(parent.context), parent, false), activity
        )
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val document = getItem(position)
        val itemHeight = (document.height.toDouble() / document.width * itemWidth).toInt()

        val options = RequestOptions()
            .placeholder(R.drawable.progress_animation)
            .override(itemWidth, itemHeight)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .error(R.drawable.img_fail)
            .dontAnimate()

        //이미지비율에 따라 이미지뷰의 크기를 조정하고 이미지 로드
        holder.itemView.img.apply {
            updateLayoutParams { height = itemHeight }
            Glide.with(activity).load(document.imageUrl).apply(options).thumbnail(0.3f).into(this)
        }.setOnClickListener {
            Intent(activity, DetailActivity::class.java).apply {
                putExtra("bundle", Bundle().apply {
                    putParcelable("document", document)
                    putInt("imgHeight", itemHeight * 2)
                })
            }.also { activity.startActivity(it) }
        }
    }
}

class ImgViewHolder(binding: ViewDataBinding, lifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.lifecycleOwner = lifecycleOwner
    }
}
