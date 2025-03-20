package com.example.multilayoutlogger.data

import androidx.lifecycle.LiveData

class NewsRepository(private val articleDao: ArticleDao) {
    val allArticles: LiveData<List<Article>> = articleDao.getAllArticles()
    val favoriteArticles: LiveData<List<Article>> = articleDao.getFavoriteArticles()

    fun getArticleById(id: Long): LiveData<Article> = articleDao.getArticleById(id)

    suspend fun insert(article: Article) = articleDao.insertArticle(article)

    suspend fun update(article: Article) = articleDao.updateArticle(article)

    suspend fun delete(article: Article) = articleDao.deleteArticle(article)
} 