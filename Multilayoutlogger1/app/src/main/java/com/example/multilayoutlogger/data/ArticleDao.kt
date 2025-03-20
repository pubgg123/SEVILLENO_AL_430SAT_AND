package com.example.multilayoutlogger.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY timestamp DESC")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles WHERE id = :articleId")
    fun getArticleById(articleId: Long): LiveData<Article>

    @Insert
    suspend fun insertArticle(article: Article): Long

    @Update
    suspend fun updateArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
} 