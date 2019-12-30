package com.example.newsapp.ui.home

import android.app.Activity
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.test.DataFrom
import com.example.newsapp.ui.ViewTypes
import com.example.newsapp.ui.adapter.NewsRecyclerAdapter
import com.example.newsapp.utils.ConnectivityReceiver
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var mAdapter:NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        tool_bar.inflateMenu(R.menu.menu)

        val data = DataFrom().getNewsList()
        mAdapter = NewsRecyclerAdapter(data)

        news_recycler_view.apply {
            layoutManager = getLayoutManager(data)
            setHasFixedSize(true)
            adapter = mAdapter
            isNestedScrollingEnabled = true
        }
    }


    private fun getLayoutManager(data: List<Article>):GridLayoutManager{
        val orientation = activity!!.resources.configuration.orientation
        var layoutManager:GridLayoutManager? = null
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
            layoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    return if (data[position].viewType == ViewTypes.ORDINARY_ARTICLE) 1
                    else 2
                }
            }
            return layoutManager
        }else{
            return GridLayoutManager(activity!!.applicationContext, 1)
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        //TODO SHOW SNACK BAR IF DON'T HAVE INTERNET CONNECTION
        Log.e("INTERNET", isConnected.toString())
    }


}
