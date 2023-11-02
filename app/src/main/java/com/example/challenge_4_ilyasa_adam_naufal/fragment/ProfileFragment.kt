package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.challenge_4_ilyasa_adam_naufal.activity.LoginMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

	private var _binding: FragmentProfileBinding? = null
	private val binding get() = _binding!!

	private lateinit var firebaseAuth: FirebaseAuth

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentProfileBinding.inflate(inflater, container, false)

		firebaseAuth = Firebase.auth

		getProfile()
		logout()

		return binding.root
	}

	private fun getProfile() {
		val _auth = FirebaseAuth.getInstance()
		val _userAuth = _auth.currentUser

		binding.tvEmail.text = _userAuth?.email
		binding.tvMobileProfile.text = _userAuth?.phoneNumber

	}

	private fun logout() {
		binding.tvLogout.setOnClickListener {
			firebaseAuth.signOut()
			val intent = Intent(requireContext(), LoginMenu::class.java)
			startActivity(intent)
		}

		binding.logoutButton.setOnClickListener {
			firebaseAuth.signOut()
			val intent = Intent(requireContext(), LoginMenu::class.java)
			startActivity(intent)
		}

	}
}