package com.osy.intern.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.osy.intern.R
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.databinding.ActivityMainBinding
import com.osy.intern.ui.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var imgRepository: ImgRepository

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewDataBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = viewModelProvider(viewModelFactory)

        with(viewDataBinding) {
            mainViewModel = this@MainActivity.mainViewModel
            lifecycleOwner = this@MainActivity
        }

        initObserve()
    }

    private fun initObserve() {
        mainViewModel.data.observe(this, Observer {
            it.documents.forEach {
                println(it.imageUrl)
            }
        })
    }
}