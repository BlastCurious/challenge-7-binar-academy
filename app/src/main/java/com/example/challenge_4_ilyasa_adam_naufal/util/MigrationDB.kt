package com.example.challenge_4_ilyasa_adam_naufal.util

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationDB : Migration(1, 2) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("ALTER TABLE cart_menu ADD COLUMN foodQuantity INTEGER NOT NULL DEFAULT 1")
	}


}