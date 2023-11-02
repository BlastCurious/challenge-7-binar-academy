package com.example.challenge_4_ilyasa_adam_naufal

import android.content.Context
import android.content.SharedPreferences

import androidx.core.content.edit


object SharedPreferences {
	private lateinit var prefs: SharedPreferences
	private const val PREF_NAME = "layout"

	fun init(context: Context) {
		prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
	}

	fun read(key: String, value: Boolean): Boolean {
		return prefs.getBoolean(key, value)
	}

	fun write(key: String, value: Boolean) {
		val prefsEditor: SharedPreferences.Editor = prefs.edit()
		with(prefsEditor) {
			putBoolean(key, value)
			apply()
			commit()
		}
	}
}
