package com.devid.feedarticlescompose.ui.login

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPrefManager: SharedPrefManager,
    private val application: Application
) : ViewModel() {

    var login = mutableStateOf("")
    var password = mutableStateOf("")

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    private val _navigateToMain = MutableSharedFlow<Unit>()
    val navigateToMain = _navigateToMain.asSharedFlow()

    init {
        validateFields()
    }

    fun updateLogin(value: String) {
        login.value = value
        validateFields()
    }

    fun updatePassword(value: String) {
        password.value = value
        validateFields()
    }

    private fun validateFields() {
        _isButtonEnabled.value = login.value.isNotBlank() && password.value.isNotBlank()
    }

    fun loginUser() {
        if (!_isButtonEnabled.value) return

        viewModelScope.launch {
            try {
                val response = apiInterface.loginUser(login.value, password.value)

                when (response?.code()) {
                    200 -> {
                        val user = response.body()
                        if (user != null) {
                            sharedPrefManager.saveUserToken(user.token)
                            sharedPrefManager.saveUserId(user.id)
                            showToast(R.string.login_success)
                            _navigateToMain.emit(Unit)
                        }
                    }
                    304 -> showToast(R.string.token_unchanged)
                    400 -> showToast(R.string.invalid_parameters)
                    401 -> showToast(R.string.invalid_parameters)
                    503 -> showToast(R.string.connect_later)
                    else -> showToast(R.string.connect_later)
                }
            } catch (e: Exception) {
                showToast(R.string.connect_later)
            }
        }
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(application, application.getString(messageId), Toast.LENGTH_SHORT).show()
    }
}