package com.example.newsapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class SearchRecyclerAdapter: RecyclerView.Adapter<SearchRecyclerAdapter.Holder>() {

    private var list:List<Article> = emptyList()
    private var listener:SearchClickListener? = null

    fun setDataList(list:List<Article>){
        this.list = list
        notifyDataSetChanged()
    }

    fun setClickListener(listener:SearchClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ordinary_news_item, parent, false)
        return Holder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.category.text = "#GENERAL"
        holder.source.text = list[position].source!!.name
        holder.title.text = list[position].title
        Picasso.get()
            .load(list[position].urlToImage)
            .into(holder.img)
    }

    inner class Holder(view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        val img = view.findViewById<ImageView>(R.id.ordinary_img)
        val category = view.findViewById<TextView>(R.id.ordinary_category)
        val source = view.findViewById<TextView>(R.id.ordinary_article_source)
        val title = view.findViewById<TextView>(R.id.ordinary_title)

        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener?.onItemClick(v, list[adapterPosition].url!!)
        }
    }

    interface SearchClickListener{
        fun onItemClick(v:View?, url:String)
    }
}