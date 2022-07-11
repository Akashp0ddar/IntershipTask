package com.akash.intershiptask.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val description:String?=null,
    val imageView: Bitmap?=null,
    val video: String =""
)

