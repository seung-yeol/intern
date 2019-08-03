package com.osy.intern.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.osy.intern.R
import com.osy.intern.data.repository.ImgRepository
import com.osy.intern.databinding.ActivityMainBinding
import com.osy.intern.ui.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.abs


class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var imgRepository: ImgRepository
    @Inject
    lateinit var adapter: ImgListAdapter

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewDataBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = viewModelProvider(viewModelFactory)

        with(viewDataBinding) {
            mainViewModel = this@MainActivity.mainViewModel
            lifecycleOwner = this@MainActivity
        }

        initRecyclerView()
        initObserve()
    }

    private fun initObserve() {
        with(mainViewModel) {
            imgData.observe(this@MainActivity, Observer {
                adapter.submitList(it)
            })

            isInit.observe(this@MainActivity, Observer {
                if (it) recyclerView.scrollToPosition(0)
                else Toast.makeText(this@MainActivity, "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initRecyclerView() {
        adapter = ImgListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var lastFirstVisible = -1
            private var lastVisibleCount = -1
            private var lastItemCount = -1

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val listPreloader = ListPreloader(Glide.with(this@MainActivity), adapter, adapter, 10)
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager

                val firstVisible = layoutManager.findFirstVisibleItemPositions(null).toList().last()
                val visibleCount = abs(firstVisible - layoutManager.findLastVisibleItemPositions(null).toList().last())
                val itemCount = recyclerView.adapter!!.itemCount

                if (firstVisible != lastFirstVisible || visibleCount != lastVisibleCount || itemCount != lastItemCount) {
                    listPreloader.onScroll(null, firstVisible, visibleCount, itemCount)
                    lastFirstVisible = firstVisible
                    lastVisibleCount = visibleCount
                    lastItemCount = itemCount
                }
            }
        })
    }
}
