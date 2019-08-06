package com.osy.intern.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
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
import com.osy.intern.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_img.view.*
import javax.inject.Inject

class ImgListAdapter @Inject constructor(private val activity: AppCompatActivity) :
    ListPreloader.PreloadSizeProvider<ImgVO.Document>, ListPreloader.PreloadModelProvider<ImgVO.Document>,
    ListAdapter<ImgVO.Document, ImgListAdapter.ImgViewHolder>(
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

    private var itemWidth = 0   // 가로길이에 비율을 곱하여 세로길이를 구할 것임.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        itemWidth = parent.width / 2
        return ImgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false))
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val document = getItem(position)
        val itemHeight = //가로세로 크기비율만큼 이미지뷰 Height 구함.
            (document.height.toDouble() / document.width.toDouble() * (itemWidth - holder.itemView.marginStart * 2)).toInt()
        val options = RequestOptions()
            .placeholder(R.drawable.progress_animation)
            .override(itemWidth, itemHeight)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .error(R.drawable.img_fail)
            .dontAnimate()  //리스트에서 움짤들 정지

        holder.img.apply {
            updateLayoutParams { height = itemHeight }
            Glide.with(activity).load(document.imageUrl).apply(options).thumbnail(0.3f).into(this)
        }.setOnClickListener {
            Intent(activity, DetailActivity::class.java).apply {
                putExtra("bundle", Bundle().apply {
                    putParcelable("document", document)
                    putInt(
                        "imgHeight",
                        (document.height.toDouble() / document.width.toDouble() * (itemWidth + holder.itemView.marginStart * 2)).toInt() * 2
                    )
                })
            }.also { activity.startActivity(it) }
        }
    }

    class ImgViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.img
    }
}
