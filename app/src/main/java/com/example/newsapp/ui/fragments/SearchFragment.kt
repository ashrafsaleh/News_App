package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.OnArticleListener
import com.example.newsapp.ui.adapter.ArticleAdapter
import com.example.newsapp.viewmodel.BreakingNewsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment(),OnArticleListener {
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var searchBtn: ImageView
    lateinit var search: EditText
    lateinit var newsAdapter: ArticleAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        viewModel = ViewModelProvider(this).get(BreakingNewsViewModel::class.java)
        searchBtn = view.findViewById(R.id.searcharticleimagebutton)
        search = view.findViewById(R.id.searchnewsedittext)
        searchBtn.setOnClickListener{
            val s = search.text.toString()
            viewModel.getSearchArticles(s).observe(viewLifecycleOwner, Observer {
                searcharticlesrecyclerview.apply {
                    adapter=ArticleAdapter(it)
                    newsAdapter = adapter as ArticleAdapter
                    newsAdapter.setOnItemClickListener(this@SearchFragment)
                }
            })
        }
        return view
    }

    override fun onclick(article: Article) {
        val action = SearchFragmentDirections.actionSearchFragmentToArticleFragment(article)
        findNavController().navigate(action)    }

}