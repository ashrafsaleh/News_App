package com.example.newsapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Article

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertArticle(article: Article)

    @Query("select * from articles")
    fun articlesList(): LiveData<List<Article>>

}
