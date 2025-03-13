package com.devid.feedarticlescompose.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefManager @Inject constructor( private val sharedPreferences: SharedPreferences) {

    fun saveUserToken(token: String?) {
        sharedPreferences.edit().putString("token", token).commit()
    }

    fun saveUserId(userId: Long) {
        sharedPreferences.edit().putLong("user_id", userId).commit()
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun getUserId(): Long {
        return sharedPreferences.getLong("user_id", 0)
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}