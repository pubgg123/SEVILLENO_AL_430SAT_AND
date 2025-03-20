package com.example.multilayoutlogger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.multilayoutlogger.data.Article
import com.example.multilayoutlogger.data.NewsDatabase
import com.example.multilayoutlogger.data.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    val allArticles: LiveData<List<Article>>
    val favoriteArticles: LiveData<List<Article>>

    init {
        val articleDao = NewsDatabase.getDatabase(application, viewModelScope).articleDao()
        repository = NewsRepository(articleDao)
        allArticles = repository.allArticles
        favoriteArticles = repository.favoriteArticles
    }

    fun getArticleById(id: Long): LiveData<Article> = repository.getArticleById(id)

    fun insert(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun update(article: Article) = viewModelScope.launch {
        repository.update(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

    fun toggleFavorite(article: Article) = viewModelScope.launch {
        article.isFavorite = !article.isFavorite
        repository.update(article)
    }
} 