package com.example.challenge_4_ilyasa_adam_naufal.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.challenge_4_ilyasa_adam_naufal.R
import com.example.challenge_4_ilyasa_adam_naufal.SharedPreferences
import com.example.challenge_4_ilyasa_adam_naufal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		SharedPreferences.init(this)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
		val navController = navHostFragment.navController

		binding.bottomNavBar.setupWithNavController(navController)

		navController.addOnDestinationChangedListener{ _, destination, _ ->
			when (destination.id) {
				R.id.detailItem -> hideBotNav()
				R.id.confirmOrderFragment -> hideBotNav()
				else -> showBotNav()
			}
		}

	}

	private fun showBotNav() {
		binding.bottomNavBar.visibility = View.VISIBLE
	}

	private fun hideBotNav() {
		binding.bottomNavBar.visibility = View.GONE
	}

}