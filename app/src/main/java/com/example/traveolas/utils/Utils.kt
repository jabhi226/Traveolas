package com.example.traveolas.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.widget.Toast
import androidx.core.content.ContextCompat


object Utils {

    fun printMessage(vararg message: Any){
        message.forEach {
            print("$it | ")
        }
        println()
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun drawableToBitmap(context: Context, d: Int): Bitmap {

        val drawable = ContextCompat.getDrawable(context, d)

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        val bitmap: Bitmap =
            if (drawable?.intrinsicWidth ?: 0 <= 0 || drawable?.intrinsicHeight ?: 0 <= 0) {
                Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                Bitmap.createBitmap(
                    drawable?.intrinsicWidth ?: 1,
                    drawable?.intrinsicHeight ?: 1,
                    Bitmap.Config.ARGB_8888
                )
            }

        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)
        return bitmap
    }

    fun convertLatLongToDMS(latitude: Double, longitude: Double): String {
        val builder = StringBuilder()
        if (latitude < 0) {
            builder.append("S ")
        } else {
            builder.append("N ")
        }
        val latitudeDegrees: String = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS)
        val latitudeSplit = latitudeDegrees.split(":").toTypedArray()
        builder.append(latitudeSplit[0])
        builder.append("°")
        builder.append(latitudeSplit[1])
        builder.append("'")
        builder.append(latitudeSplit[2])
        builder.append("\"")
        builder.append(" ")
        if (longitude < 0) {
            builder.append("W ")
        } else {
            builder.append("E ")
        }
        val longitudeDegrees: String =
            Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS)
        val longitudeSplit = longitudeDegrees.split(":").toTypedArray()
        builder.append(longitudeSplit[0])
        builder.append("°")
        builder.append(longitudeSplit[1])
        builder.append("'")
        builder.append(longitudeSplit[2])
        builder.append("\"")
        return builder.toString()
    }
}