package com.devid.feedarticlescompose.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _goToMainScreenSharedFlow = MutableSharedFlow<Boolean>()
    val goToMainScreenSharedFlow = _goToMainScreenSharedFlow.asSharedFlow()

    private val _goToLoginScreenSharedFlow = MutableSharedFlow<Boolean>()
    val goToLoginScreenSharedFlow = _goToLoginScreenSharedFlow.asSharedFlow()

    init {
        navigateToMainAfterDelay()
    }

    fun navigateToMainAfterDelay() {
        val token = sharedPrefManager.getUserToken()
        viewModelScope.launch {
            delay(1500)
            if (token.isNullOrEmpty()) {
                _goToLoginScreenSharedFlow.emit(true)
            } else{
                _goToMainScreenSharedFlow.emit(true)
            }
        }
    }
}