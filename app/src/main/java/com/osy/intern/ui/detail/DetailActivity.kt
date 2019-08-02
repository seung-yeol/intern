package com.osy.intern.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
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
        Glide.with(this).load(document.imageUrl).into(img)

        txtDocURL.text = document.docUrl
        txtSource.text = document.displaySitename
        txtDatetime.text = document.datetime
    }
}