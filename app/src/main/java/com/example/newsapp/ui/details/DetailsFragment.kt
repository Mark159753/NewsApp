package com.example.newsapp.ui.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.findNavController

import com.example.newsapp.R
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val url = arguments?.getString("url")

        web_browser.apply {
            loadUrl(url)
            webViewClient = MyWebViewClient()
        }

        backPress_arrow.setOnClickListener { backPress_arrow.findNavController().popBackStack() }

        requireActivity().onBackPressedDispatcher.addCallback(this){
            onBackPress()
        }
    }


    private fun onBackPress(){
        if (web_browser.canGoBack()){
            web_browser.goBack()
        }else{
            web_browser.findNavController().popBackStack()
        }
    }


    class MyWebViewClient: WebViewClient(){

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }

}
