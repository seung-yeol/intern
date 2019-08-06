package com.osy.intern.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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
    lateinit var imgListAdapter: ImgListAdapter

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = viewModelProvider(viewModelFactory)
        with(DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)) {
            mainViewModel = this@MainActivity.mainViewModel
            lifecycleOwner = this@MainActivity
        }

        setSupportActionBar(toolbar)
        initRecyclerView()
        initObserve()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = ImgListAdapter(this@MainActivity).also { imgListAdapter = it }
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            //RecyclerViewPreloader는 기본적으로 LinearLayoutManager를 베이스로 하고 있어서 다른 매니저를 사용하는 경우 새로이 OnScrollListener를 만들어서 사용해야함.
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                private var lastFirstVisible = -1
                private var lastVisibleCount = -1
                private var lastItemCount = -1

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    //최대 10개 미리 로드.
                    val listPreloader = ListPreloader(Glide.with(this@MainActivity), imgListAdapter, imgListAdapter, 10)
                    val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager

                    val firstVisible = layoutManager.findFirstVisibleItemPositions(null).toList().first()
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

    private fun initObserve() {
        with(mainViewModel) {
            //imgData갱신 되면 리스트어뎁터에 갱신 요청
            imgData.observe(this@MainActivity, Observer {
                imgListAdapter.submitList(it)
            })

            //리스트에 보여줄 데이터 초기화성공하면 스크롤 최상위, 실패하는 경우는 검색어입력 하라고 토스트메시지 노출
            isInit.observe(this@MainActivity, Observer {
                if (it) {
                    recyclerView.scrollToPosition(0)
                } else Toast.makeText(this@MainActivity, "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show()
            })

            //키보드 내림.
            doKeyboardHide.observe(this@MainActivity, Observer {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    editSearch.windowToken, 0
                )
            })
        }
    }
}
