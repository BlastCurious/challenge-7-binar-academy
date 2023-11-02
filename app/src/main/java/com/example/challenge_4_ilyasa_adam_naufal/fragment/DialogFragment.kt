package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.challenge_4_ilyasa_adam_naufal.R
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentDialogBinding

class DialogFragment : DialogFragment() {
	private lateinit var binding: FragmentDialogBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentDialogBinding.inflate(layoutInflater, container, false)

		backtoMenu()
		return binding.root
	}

	private fun backtoMenu() {
		binding.backToMenu.setOnClickListener {
			findNavController().navigate(R.id.homeFragment)
		}
	}
}