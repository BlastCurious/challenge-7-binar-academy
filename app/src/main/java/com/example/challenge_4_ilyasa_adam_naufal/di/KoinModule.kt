package com.example.challenge_4_ilyasa_adam_naufal.di

import com.example.challenge_4_ilyasa_adam_naufal.api.APIClient
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDatabase
import com.example.challenge_4_ilyasa_adam_naufal.repository.SimpleRepository
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.SimpleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
	val dataModule
		get() = module {
			//DATABASE
			single { CartDatabase.getInstance(context = get()) }
			//API
			single { APIClient.instance }

			//REPOSITORY
			factory { SimpleRepository(get<CartDatabase>().cartDao)}
		}


	val uiModule
		get() = module {
			viewModel { SimpleViewModel(get())}
		}
}