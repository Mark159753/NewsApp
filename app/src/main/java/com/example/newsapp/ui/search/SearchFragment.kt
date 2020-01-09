package com.example.newsapp.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApp

import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.BaseViewModelFactory
import com.example.newsapp.ui.search.adapter.SearchRecyclerAdapter
import kotlinx.android.synthetic.main.details_fragment.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragment : Fragment(), SearchRecyclerAdapter.SearchClickListener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private lateinit var mAdapter:SearchRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!!.application as NewsApp).getAppComponent().fragmentComponent().plusActivity(activity!!)
            .create().inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        createToolBar()
        createSearchField()
        initSearchRecyclerView()
    }

    private fun initSearchRecyclerView(){
        mAdapter = SearchRecyclerAdapter()
        mAdapter.setClickListener(this)
        search_recycler_view.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
            setHasFixedSize(true)
        }
    }

    private fun createSearchField(){
        search_field.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                search_progress_bar.visibility = View.VISIBLE
                coroutineScope.launch {
                    val response = viewModel.makeSearch(s.toString())
                    setResponseObserver(response)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setResponseObserver(response:LiveData<List<Article>>){
        response.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            search_progress_bar.visibility = View.GONE
            mAdapter.setDataList(it)
        })
    }

    override fun onItemClick(v: View?, url: String) {
        v!!.findNavController().navigate(R.id.detailsFragment, bundleOf("url" to url))
    }

    private fun createToolBar(){
        search_actionBar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                this.findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}
