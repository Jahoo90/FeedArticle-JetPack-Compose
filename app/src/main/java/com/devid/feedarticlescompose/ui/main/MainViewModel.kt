package com.devid.feedarticlescompose.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.network.dtos.ArticlesResponseItem
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticlesResponseItem>>(emptyList())
    val articles: StateFlow<List<ArticlesResponseItem>> = _articles.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    private val _selectedCategory = MutableStateFlow(0)
    val selectedCategory: StateFlow<Int> = _selectedCategory.asStateFlow()

    private val _allArticles = MutableStateFlow<List<ArticlesResponseItem>>(emptyList())

    private val _categoryColor = MutableStateFlow(0)
    val categoryColor: StateFlow<Int> = _categoryColor.asStateFlow()

    init {
        fetchArticles()
        Log.i("jahoo", "Token is : ${sharedPrefManager.getUserToken()} \n User Id is : ${sharedPrefManager.getUserId()}")
        Log.i("jahoo", "Articles are : ${_articles.value}")
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            val token = sharedPrefManager.getUserToken()
            try {
                val response = apiInterface.getAllArticles(token.toString())
                if (response!!.isSuccessful) {
                    response.body()?.let { articles ->
                        _allArticles.value = articles
                        _articles.value =  articles
                    } ?: _errorMessage.emit("Response body is null")
//                    val body = response.body()
//                    if (body != null) {
//                        _articles.value = body
//                    } else {
//                        _errorMessage.emit("Response body is null")
//                    }
                } else {
                    _errorMessage.emit("Error fetching articles from API: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.emit(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterArticlesByCategory(category: Int) {
        _selectedCategory.value = category
        _articles.value = if (category == 0) {
            _allArticles.value
        } else {
            _allArticles.value.filter { it.categorie == category }
        }
    }

    fun logout() {
        sharedPrefManager.clearUserData()
    }
}