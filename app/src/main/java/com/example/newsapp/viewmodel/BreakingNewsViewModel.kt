package com.example.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.room.ArticlesDataBase
import com.example.newsapp.model.Article
import com.example.newsapp.repositories.BreakingNewsRepository
import kotlinx.coroutines.launch

class BreakingNewsViewModel(application: Application) : AndroidViewModel(application) {
    private val breakingNewsRepo: BreakingNewsRepository
    var articleList: LiveData<List<Article>>
    lateinit var searchArticleList: LiveData<List<Article>>
    private var favoriteList:LiveData<List<Article>>


    init {
        val favDao = ArticlesDataBase.getAppDataBase(application)!!.articleDao()!!
        breakingNewsRepo = BreakingNewsRepository(favDao)
        this.articleList = breakingNewsRepo.getAllArticles()
        this.favoriteList=breakingNewsRepo.getOfflineArticles()
    }

    fun insertArticle(article: Article) {
        breakingNewsRepo.insertArticle(article)
    }
    fun getArticles(): LiveData<List<Article>> {
        return articleList
    }

    fun getSearchArticles(s: String): LiveData<List<Article>> {
        searchArticleList = breakingNewsRepo.getSearchArticles(s)
        return searchArticleList
    }
    fun getOfflineArticles():LiveData<List<Article>>{
        return favoriteList
    }

}