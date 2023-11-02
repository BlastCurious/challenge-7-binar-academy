package com.example.challenge_4_ilyasa_adam_naufal.database.cart

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_menu")
data class Cart (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "food_name")
    var itemName: String,
    @ColumnInfo(name = "img_Id")
    var imgId: String,
    @ColumnInfo(name ="food_Price")
    var itemPrice: Int? = 0,
    @ColumnInfo(name = "food_Quantity")
    var itemQuantity: Int = 1,
    @ColumnInfo(name = "total_Price")
    var totalPrice: Int? = 0,
    @ColumnInfo(name = "food_Note")
    var itemNote: String?

):Parcelable
