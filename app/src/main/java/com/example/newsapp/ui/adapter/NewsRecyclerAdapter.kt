package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.ViewTypes
import com.squareup.picasso.Picasso
import java.lang.IllegalArgumentException

class NewsRecyclerAdapter(
    private val listData:List<Article>
) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var listener:OnNewsClickListener? = null

    fun setListener(listener: OnNewsClickListener){
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].viewType!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType){
            ViewTypes.MAIN_ARTICLE -> {
                val v = inflater.inflate(R.layout.main_article_item, parent, false)
                return MainArticleViewHolder(v)
            }
            ViewTypes.ORDINARY_ARTICLE -> {
                val v = inflater.inflate(R.layout.ordinary_news_item, parent, false)
                return OrdinaryArticleViewHolder(v)
            }
            ViewTypes.BEIGE_ARTICLE -> {
                val v = inflater.inflate(R.layout.beige_news_item, parent, false)
                return BeigeArticleViewHolder(v)
            }
            ViewTypes.TITLE_GROUP -> {
                val v = inflater.inflate(R.layout.title_item, parent, false)
                return TitleViewHolder(v)
            }
            else -> throw IllegalArgumentException("No such viewType")
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listData[position]
        when(item.viewType){
            ViewTypes.MAIN_ARTICLE -> {
                val h = holder as MainArticleViewHolder
                h.title.text = item.title
                h.source.text = item.source!!.name
                Picasso.get()
                    .load(item.urlToImage)
                    .into(h.img)
            }
            ViewTypes.ORDINARY_ARTICLE -> {
                val h = holder as OrdinaryArticleViewHolder
                h.category.text = item.category
                h.source.text = item.source!!.name
                h.title.text = item.title
                Picasso.get()
                    .load(item.urlToImage)
                    .into(h.img)
            }
            ViewTypes.BEIGE_ARTICLE -> {
                val h = holder as BeigeArticleViewHolder
                h.category.text = item.category
                h.details.text = item.description
                h.source.text = item.source!!.name
                h.title.text = item.title
                Picasso.get()
                    .load(item.urlToImage)
                    .into(h.img)
            }
            ViewTypes.TITLE_GROUP -> {
                val h = holder as TitleViewHolder
                h.title.text = item.title
            }
        }
    }


    inner class MainArticleViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener{
        val img = view.findViewById<ImageView>(R.id.main_img_article)
        val title = view.findViewById<TextView>(R.id.main_article_title)
        val source = view.findViewById<TextView>(R.id.main_article_source)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.newsClick(v, listData[adapterPosition].url!!)
        }
    }

    inner class BeigeArticleViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener{
        val img = view.findViewById<ImageView>(R.id.beige_img)
        val title = view.findViewById<TextView>(R.id.beige_title)
        val category = view.findViewById<TextView>(R.id.beige_category)
        val source = view.findViewById<TextView>(R.id.beige_article_source)
        val details = view.findViewById<TextView>(R.id.beige_details)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.newsClick(v, listData[adapterPosition].url!!)
        }
    }

    inner class OrdinaryArticleViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener{
        val img = view.findViewById<ImageView>(R.id.ordinary_img)
        val category = view.findViewById<TextView>(R.id.ordinary_category)
        val source = view.findViewById<TextView>(R.id.ordinary_article_source)
        val title = view.findViewById<TextView>(R.id.ordinary_title)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.newsClick(v, listData[adapterPosition].url!!)
        }
    }

    inner class TitleViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.title_title)
    }

    interface OnNewsClickListener{
        fun newsClick(view: View?, url:String)
    }
}