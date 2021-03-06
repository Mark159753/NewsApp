package com.example.newsapp.ui.home

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.NewsApp


import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.BaseViewModelFactory
import com.example.newsapp.ui.ViewTypes
import com.example.newsapp.ui.adapter.NewsRecyclerAdapter
import com.example.newsapp.utils.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListener, NewsRecyclerAdapter.OnNewsClickListener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject lateinit var viewModelFactory: BaseViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private lateinit var mAdapter:NewsRecyclerAdapter
    private lateinit var content:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        content = inflater.inflate(R.layout.home_fragment, container, false)
        return content
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as NewsApp).getAppComponent().fragmentComponent().plusActivity(activity!!)
            .create().inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        createToolBar()
        bindUI()
    }

    private fun createToolBar(){
        tool_bar.inflateMenu(R.menu.menu)
        tool_bar.menu.findItem(R.id.search_btn).setOnMenuItemClickListener {
            if (it.itemId.equals(R.id.search_btn)){
                content.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                return@setOnMenuItemClickListener true
            }else return@setOnMenuItemClickListener false
        }
    }

    private fun bindUI() = coroutineScope.launch {
        val newsList = viewModel.news.await()

        newsList.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            progress_bar.visibility = View.GONE
            showNews(it)
        })
    }

    private fun showNews(data:List<Article>){
        mAdapter = NewsRecyclerAdapter(data)
        mAdapter.setListener(this)

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
        if (!isConnected) {
            showSnackBar()
        }
        Log.e("INTERNET", isConnected.toString())
    }

    private fun showSnackBar(){
        Snackbar.make(content, "You Don't Connect To Internet", Snackbar.LENGTH_LONG).show()
    }

    override fun newsClick(view: View?, url: String) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailsFragment, bundleOf("url" to url))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val position = scroll_wrapper?.verticalScrollbarPosition
        position?.let { outState.putInt("scrollWrapperPosition", it) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val position = savedInstanceState?.getInt("scrollWrapperPosition")
        position?.let { scroll_wrapper.scrollY = it }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}
