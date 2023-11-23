package com.example.challenge_4_ilyasa_adam_naufal.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AuthVerificationTest{
	@Test
	fun checkEmailValid() {
		val email = "yourmail@gmail.com"
		val result = AuthVerification.emailVerif(email)

		assertThat(result).isTrue()
	}

	@Test
	fun checkEmailIsEmpty() {
		val email = ""
		val result = AuthVerification.emailVerif(email)

		assertThat(result).isFalse()
	}

	@Test
	fun checkEmailIsNotValid() {
		val email = "someMail.gmail.com"
		val result = AuthVerification.emailVerif(email)

		assertThat(result).isFalse()
	}

	@Test
	fun checkEmailDoubleDotReturnFalse() {
		val email = "youremail@gmail..com"
		val result = AuthVerification.emailVerif(email)

		assertThat(result).isFalse()
	}

	@Test
	fun checkEmailWithoutDomainReturnFalse() {
		val email = "youremail@gmail"
		val result = AuthVerification.emailVerif(email)

		assertThat(result).isFalse()
	}


	@Test
	fun checkPasswordIsValid() {
		val password = "password123"
		val result = AuthVerification.pwVerif(password)

		assertThat(result).isTrue()
	}

	@Test
	fun checkPasswordIsEmpty() {
		val password = ""
		val result = AuthVerification.pwVerif(password)

		assertThat(result).isFalse()
	}

	@Test
	fun checkPasswordLessThanSixCharacters() {
		val password = "lei"
		val result = AuthVerification.pwVerif(password)

		assertThat(result).isFalse()
	}
}
