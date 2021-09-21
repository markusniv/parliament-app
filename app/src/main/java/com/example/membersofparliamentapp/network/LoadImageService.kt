package com.example.membersofparliamentapp.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.lang.Exception
import java.net.URL

fun loadImageOnline(imageView: ImageView, url: String, handler: Handler) {
    var image: Bitmap? = null
    try {
        val `in` = URL(url).openStream()
        image = BitmapFactory.decodeStream(`in`)

        handler.post {
            imageView.setImageBitmap(image)
        }
    } catch (e: Exception) {
        Log.i("Image load fail", e.toString())
    }
}