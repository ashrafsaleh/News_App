package com.example.newsapp.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(), OnArticleListener {
    lateinit var viewModel: BreakingNewsViewModel
    lateinit var newsAdapter: ArticleAdapter
    lateinit var fab: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breaking_news, container, false)
        viewModel = ViewModelProvider(this).get(BreakingNewsViewModel::class.java)
        fab = view.findViewById(R.id.reload)

        if (isNetworkAvailable()) {
            viewModel.getArticles().observe(
                viewLifecycleOwner, Observer {
                    for (i in it) {
                        viewModel.insertArticle(i)
                    }
                    breakingnewsrecyclerview.apply {
                        adapter = ArticleAdapter(it)
                        newsAdapter = adapter as ArticleAdapter
                        newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
                    }
                }
            )
        }
        else {
            viewModel.getOfflineArticles().observe(viewLifecycleOwner, Observer {
                breakingnewsrecyclerview.apply {
                    adapter = ArticleAdapter(it)
                    newsAdapter = adapter as ArticleAdapter
                    newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
                }
            })
        }
        fab.setOnClickListener{
            if (isNetworkAvailable()) {
                viewModel.getArticles().observe(
                    viewLifecycleOwner, Observer {
                        for (i in it) {
                            viewModel.insertArticle(i)
                        }
                        breakingnewsrecyclerview.apply {
                            adapter = ArticleAdapter(it)
                            newsAdapter = adapter as ArticleAdapter
                            newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
                            Toast.makeText(context,"Your list is updated",Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
            else {
                viewModel.getOfflineArticles().observe(viewLifecycleOwner, Observer {
                    breakingnewsrecyclerview.apply {
                        Toast.makeText(context,"There is no network",Toast.LENGTH_LONG).show()
                        adapter = ArticleAdapter(it)
                        newsAdapter = adapter as ArticleAdapter
                        newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)

                    }
                })
            }
        }

        // Inflate the layout for this fragment*/
        return view
    }

    override fun onclick(article: Article) {
        val action =
            BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}