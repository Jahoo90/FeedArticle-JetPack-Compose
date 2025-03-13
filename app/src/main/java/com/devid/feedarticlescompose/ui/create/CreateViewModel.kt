package com.devid.feedarticlescompose.ui.create

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.network.dtos.NewArticleDto
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPreferencesManager: SharedPrefManager
) : ViewModel() {

    private val token = sharedPreferencesManager.getUserToken()
    private val userId = sharedPreferencesManager.getUserId()

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var imageUrl by mutableStateOf("")
        private set


    var selectedCategory by mutableStateOf(3)
        private set

    fun onTitleChange(newValue: String) {
        title = newValue
    }

    fun onDescriptionChange(newValue: String) {
        description = newValue
    }

    fun onImageUrlChange(newValue: String) {
        imageUrl = newValue
    }

    fun onCategoryChange(newCategory: Int) {
        selectedCategory = newCategory
    }

    fun createArticle(context: Context) {
        viewModelScope.launch {
            try {
                val article = NewArticleDto(
                    userId = userId,
                    title = title,
                    description = description,
                    imageUrl = imageUrl,
                    category = selectedCategory
                )

                val response = apiInterface.createArticle(token.toString(), article)

                val message = when (response?.code()) {
                    201 -> context.getString(R.string.create_success)
                    401 -> context.getString(R.string.create_access)
                    else -> context.getString(R.string.create_failed)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.connection_problem), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}