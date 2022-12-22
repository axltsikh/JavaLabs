package com.example.fifteenlab

import android.app.IntentService
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class ImageIntentService : IntentService("ImageIntentService") {
    private lateinit var storage:String
    override fun onHandleIntent(intent: Intent?) {
        storage=this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
        var url:String? = intent?.getStringExtra("image")
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post(Runnable {
            Picasso.with(this)
                .load(url)
                .into(url?.let { target(it) })
        })

    }
    private fun target(url:String): Target {
        var target: Target =object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val filename = "${System.currentTimeMillis()}.jpg"
                var fos: OutputStream? = null
                contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = imageUri?.let { resolver.openOutputStream(it) }
                }
                fos?.use {
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    Toast.makeText(baseContext , "Картинка успешно сохранена" , Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        }
        return target
    }

}