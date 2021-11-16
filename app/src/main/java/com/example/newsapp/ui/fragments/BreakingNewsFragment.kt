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
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment() ,OnArticleListener{
    lateinit var viewModel: BreakingNewsViewModel
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
        viewModel = ViewModelProvider(this).get(BreakingNewsViewModel::class.java)
        Log.e("test",isNetworkAvailable().toString())
        if(isNetworkAvailable()){
            viewModel.getArticles().observe(
                viewLifecycleOwner, Observer {
                    for (i in it){
                        viewModel.insertArticle(i)
                    }
                    breakingnewsrecyclerview.apply {
                        adapter=ArticleAdapter(it)
                        newsAdapter = adapter as ArticleAdapter
                        newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
                    }
                }
            )
            Toast.makeText(context,"available",Toast.LENGTH_LONG).show()
        }
        else if (isNetworkAvailable()==false){

            Toast.makeText(context,"Not available",Toast.LENGTH_LONG).show()
        }


        /*else{
            viewModel.getOfflineArticles().observe(viewLifecycleOwner, Observer {
                /*breakingnewsrecyclerview.apply {
                    adapter=ArticleAdapter(it)
                    newsAdapter = adapter as ArticleAdapter
                    newsAdapter.setOnItemClickListener(this@BreakingNewsFragment)
                }*/
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
            })
        }*/

        // Inflate the layout for this fragment*/
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onclick(article: Article) {
        val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(article)
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