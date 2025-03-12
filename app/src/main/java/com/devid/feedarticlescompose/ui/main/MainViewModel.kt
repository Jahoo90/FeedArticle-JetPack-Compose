package com.devid.feedarticlescompose.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.network.dtos.ArticlesResponseItem
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticlesResponseItem>>(emptyList())
    val articles: StateFlow<List<ArticlesResponseItem>> = _articles.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

//    init {
//        fetchArticles()
//    }

    fun fetchArticles() {
        viewModelScope.launch {
            mainRepository.getAllArticles()
                .onSuccess { _articles.value = it}
                .onFailure { _errorMessage.value = it.message }
//            Log.d("jahoo", "msg from viewModel : fetchArticles : ${articles.value.size}")
        }
    }

    fun logout() {
        sharedPrefManager.clearUserData()
    }
}