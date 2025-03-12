package com.devid.feedarticlescompose.ui.main

import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.network.dtos.ArticlesResponseItem
import com.devid.feedarticlescompose.utils.SharedPrefManager
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPrefManager: SharedPrefManager
) {

    suspend fun getAllArticles(): Result<List<ArticlesResponseItem>> {
        val token = sharedPrefManager.getUserToken()

        return try {
//            viemModelScope.launch {

            val response = apiInterface.getAllArticles(token.toString())
            if (response!!.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No Data"))
            } else {
                Result.failure(Exception("Error message : ${response?.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}