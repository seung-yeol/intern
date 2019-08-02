package com.osy.intern.ui.main.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.updateLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osy.intern.data.vo.ImgVO
import com.osy.intern.databinding.ItemImgBinding
import com.osy.intern.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_img.view.*
import javax.inject.Inject

class ImgListAdapter @Inject constructor(private val activity: AppCompatActivity) :
    ListAdapter<ImgVO.Document, ImgViewHolder>(
        object : DiffUtil.ItemCallback<ImgVO.Document>() {
            override fun areItemsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ImgVO.Document, newItem: ImgVO.Document): Boolean =
                oldItem == newItem
        }
    ) {

    private var itemWidth = 0   //가로세로 이미지 크기비율을 맞추기위해서 구함
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        itemWidth = parent.measuredWidth / 2
        return ImgViewHolder(
            ItemImgBinding.inflate(LayoutInflater.from(parent.context), parent, false), activity
        )
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val document = getItem(position)
        val itemHeight = (document.height.toDouble() / document.width * itemWidth).toInt()

        //이미지비율에 따라 이미지뷰의 크기를 조정하고 이미지 로드
        holder.itemView.img.apply {
            updateLayoutParams { height = itemHeight }
            Glide.with(this).load(document.imageUrl).into(this)
        }.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra("bundle", Bundle().apply {
                    putParcelable("document", document)
                    putInt("imgHeight", itemHeight * 2)
                })
            }

            //공유이미지 애니메이션 적용하여 액티비티 실행
            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, Pair.create(it, "imgTransition"))

            activity.startActivity(intent, optionsCompat.toBundle())
        }
    }
}

class ImgViewHolder(binding: ViewDataBinding, lifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.lifecycleOwner = lifecycleOwner
    }
}
