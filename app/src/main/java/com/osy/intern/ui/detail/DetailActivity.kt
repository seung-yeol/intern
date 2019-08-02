package com.osy.intern.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.osy.intern.R
import com.osy.intern.data.vo.ImgVO
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var document : ImgVO.Document
    var imgHeight : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        with(intent.getBundleExtra("bundle")){
            document = getParcelable("document")
            imgHeight = getInt("imgHeight")
        }

        initView()
    }

    private fun initView() {
        img.updateLayoutParams { height = imgHeight }
        Glide.with(this).load(document.imageUrl).thumbnail(0.3f).into(img)

        txtDocURL.text = document.docUrl
        txtSource.text = document.displaySitename
        txtDatetime.text = document.datetime
    }
}