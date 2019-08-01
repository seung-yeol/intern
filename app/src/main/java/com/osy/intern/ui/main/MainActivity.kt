package com.osy.intern.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.osy.intern.R
import com.osy.intern.data.Sort
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.data.vo.ImgVO
import com.osy.intern.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var imgRepository: ImgRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewDataBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        imgRepository.doGetImageList("아이유", Sort.Accuracy, 3, 5).observe(this, Observer {
          it.documents.forEach {
              println(it.imageUrl)
          }
        })
    }
}