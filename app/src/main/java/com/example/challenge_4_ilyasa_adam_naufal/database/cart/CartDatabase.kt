package com.example.challenge_4_ilyasa_adam_naufal.database.cart

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.challenge_4_ilyasa_adam_naufal.MigrationDB

@Database(
	entities = [Cart::class], version = 2)
abstract class CartDatabase : RoomDatabase() {

	abstract val cartDao: CartDao

	companion object {

		@Volatile
		private var INSTANCE: CartDatabase? = null

		fun getInstance(context: Context): CartDatabase {
			synchronized(this) {
				var instance = INSTANCE

				if (instance == null) {
					synchronized(CartDatabase::class.java){
						instance = Room.databaseBuilder(
							context.applicationContext,
							CartDatabase::class.java, "cart_database"
						)
							.fallbackToDestructiveMigration()
							//.addMigrations(MigrationDB())
							.build()
					}

				}
				return instance as CartDatabase
			}
		}
	}
}
