package com.example.challenge_4_ilyasa_adam_naufal

import android.app.Application
import com.example.challenge_4_ilyasa_adam_naufal.di.KoinModule.dataModule
import com.example.challenge_4_ilyasa_adam_naufal.di.KoinModule.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin{
			androidContext(this@App)
			modules(
				listOf(
					dataModule,
					uiModule
				)
			)
		}
	}
}