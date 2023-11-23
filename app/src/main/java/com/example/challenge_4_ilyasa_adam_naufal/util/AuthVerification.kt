package com.example.challenge_4_ilyasa_adam_naufal.util

import java.util.regex.Pattern

object AuthVerification {

	fun emailVerif(email: String): Boolean {
		return Pattern.compile(
			"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
					+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
					+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
					+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
		).matcher(email).matches()
	}

	fun pwVerif(password: String): Boolean {
		if (password.length > 6 && password.isNotEmpty()) {
			return true
		}
		return false
	}

}