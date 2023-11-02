package com.example.challenge_4_ilyasa_adam_naufal.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.Profile
import com.example.challenge_4_ilyasa_adam_naufal.databinding.ActivitySingupMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpMenu : AppCompatActivity() {
	private lateinit var binding: ActivitySingupMenuBinding
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var database: FirebaseDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySingupMenuBinding.inflate(layoutInflater)
		setContentView(binding.root)

		firebaseAuth = Firebase.auth

		fillData()

		binding.tvLogintext.setOnClickListener {
			startActivity(Intent(this, LoginMenu::class.java))
			finish()
		}

	}

	private fun fillData() {

		binding.btnSignup.setOnClickListener {
			val emailUser = binding.etEmailSignup.text.toString()
			val addressUser = binding.etAddress.text.toString()
			val mobileUser = binding.etMobile.text.toString()
			val passUser = binding.etPwSignup.text.toString()
			val intent = Intent(this, LoginMenu::class.java)
			startActivity(intent)

			when {
				emailUser.isEmpty() -> {
					Toast.makeText(this, "Email belum terisi", Toast.LENGTH_SHORT).show()
					binding.etEmailSignup.requestFocus()
					return@setOnClickListener
				}

				mobileUser.isEmpty() -> {
					Toast.makeText(this, "No Telepon belum terisi", Toast.LENGTH_SHORT).show()
					binding.etMobile.requestFocus()
					return@setOnClickListener
				}

				passUser.isEmpty() -> {
					Toast.makeText(this, "Password Belum terisi", Toast.LENGTH_SHORT).show()
					binding.etPwSignup.requestFocus()
					return@setOnClickListener
				}

				addressUser.isEmpty() -> {
					Toast.makeText(this, "Alamat Belum terisi", Toast.LENGTH_SHORT).show()
					binding.etAddress.requestFocus()
					return@setOnClickListener
				}
			}
			signup(emailUser, mobileUser, passUser, addressUser)
		}


	}

	private fun signup(
		emailUser: String,
		passUser: String,
		addressUser: String,
		mobileUser: String
	) {
		firebaseAuth.createUserWithEmailAndPassword(emailUser, passUser)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					val dbUser =
						database.reference.child("user").child(firebaseAuth.currentUser!!.uid)
					val user =
						Profile(addressUser, emailUser, mobileUser, firebaseAuth.currentUser!!.uid)

					dbUser.setValue(user).addOnCompleteListener {
						if (it.isSuccessful)
							Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
						val intent = Intent(this, LoginMenu::class.java)
						startActivity(intent)
						finish()
					}
				} else {
					Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
				}
			}
	}

}
