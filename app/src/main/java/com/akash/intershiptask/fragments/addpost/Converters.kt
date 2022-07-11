package com.akash.intershiptask.fragments.addpost

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        var curentBitmap = if(bitmap!= null)  bitmap else Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888)

        val outputStream = ByteArrayOutputStream()
        curentBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        var retImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        if (retImage==null){
            retImage = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888)
        }

        return retImage
    }

}