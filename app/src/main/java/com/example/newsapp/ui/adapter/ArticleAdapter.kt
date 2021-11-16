package com.example.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.OnArticleListener
import com.squareup.picasso.Picasso

class ArticleAdapter(private val savedArticles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articleImage: ImageView = itemView.findViewById(R.id.newsimage)
        var articleTitle: TextView = itemView.findViewById(R.id.newstitle)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v =
            LayoutInflater.from(parent.context).inflate(R.layout.newsrecycleritemrow, parent, false)

        return ArticleAdapter.ViewHolder(v)
    }

    private var onItemClickListener: OnArticleListener? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            holder.articleTitle.text = savedArticles[position].title
            Picasso.get().load(savedArticles[position].urlToImage).into(holder.articleImage)

            setOnClickListener {
                onItemClickListener?.onclick(savedArticles[position]!!)

            }
        }
    }

    override fun getItemCount() = savedArticles.size

    fun setOnItemClickListener(listener: OnArticleListener) {
        onItemClickListener = listener
    }
}