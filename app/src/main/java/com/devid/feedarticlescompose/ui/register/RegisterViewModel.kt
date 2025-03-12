package com.devid.feedarticlescompose.ui.register

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid.feedarticlescompose.R
import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.network.dtos.AuthDto
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPrefManager: SharedPrefManager,
    private val application: Application
) : ViewModel() {

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    private val _navigateToMain = MutableSharedFlow<Unit>()
    val navigateToMain = _navigateToMain.asSharedFlow()

    init {
        validateFields()
    }

    fun updateLogin(value: String) {
        _login.value = value
        validateFields()
    }

    fun updatePassword(value: String) {
        _password.value = value
        validateFields()
    }

    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
        validateFields()
    }

    private fun validateFields() {
        _isButtonEnabled.value = _login.value.isNotBlank() &&
                _password.value.isNotBlank() &&
                _confirmPassword.value.isNotBlank() &&
                _password.value == _confirmPassword.value
    }

    fun registerUser() {
        if (!_isButtonEnabled.value) return

        viewModelScope.launch {
            try {
                val response = apiInterface.registerUser(AuthDto(_login.value, _password.value))

                when (response?.code()) {
                    200 -> {
                        response.body()?.let {
                            sharedPrefManager.saveUserToken(it.token)
                            sharedPrefManager.saveUserId(it.id)
                            showToast(R.string.register_success)
                            _navigateToMain.emit(Unit)
                        }
                    }
                    303 -> showToast(R.string.login_already_used)
                    304 -> showToast(R.string.connect_later)
                    400 -> showToast(R.string.connect_later)
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