package com.osy.intern.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import com.osy.intern.R
import com.osy.intern.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewDataBinding: ActivityMainBinding
                = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}
