package com.plm.hostifyapp.utils

import android.content.Context
import java.util.*

object SessionManagerUtil {

    private const val SESSION_PREFERENCES = "com.plm.hostifyapp.session_manager.SESSION_PREFERENCES"
    private const val SESSION_EXPIRY_TIME = "com.plm.hostifyapp.session_manager.SESSION_EXPIRY_TIME"
    private const val SESSION_TOKEN = "com.plm.hostifyapp.session_manager.SESSION_TOKEN"
    private const val SESSION_USERNAME = "com.plm.hostifyapp.session_manager.SESSION_USERNAME"

    fun startUserSession(context: Context, expiresIn: Int) {
        val calendar = Calendar.getInstance()
        val userLoggedInTime = calendar.time
        calendar.time = userLoggedInTime
        calendar.add(Calendar.SECOND, expiresIn)
        val expiryTime = calendar.time
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.time)
        editor.apply()
    }

    fun storeUserToken(context: Context, token: String) {
        val tokenEditor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        tokenEditor.putString(SESSION_TOKEN, token)
        tokenEditor.apply()
    }

    fun storeUserName(context: Context, username: String) {
        val tokenEditor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        tokenEditor.putString(SESSION_USERNAME, username)
        tokenEditor.apply()
    }

    fun isSessionActive(currentTime: Date, context: Context) : Boolean {
        val sessionExpiresAt = Date(getExpiryDateFromPreferences(context)!!)
        return !currentTime.after(sessionExpiresAt)
    }

    private fun getExpiryDateFromPreferences(context: Context) : Long? {
        return context.getSharedPreferences(SESSION_PREFERENCES, 0).getLong(SESSION_EXPIRY_TIME, 0)
    }

    fun endUserSession(context: Context) {
        clearStoredData(context)
    }
    private fun clearStoredData(context: Context) {
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.clear()
        editor.apply()
    }}