package com.devid.feedarticlescompose.ui.create

import androidx.lifecycle.ViewModel
import com.devid.feedarticlescompose.network.ApiInterface
import com.devid.feedarticlescompose.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sharedPreferencesManager: SharedPrefManager
) : ViewModel() {

}