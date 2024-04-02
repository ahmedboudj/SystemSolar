package com.example.solarsystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas

class VaisseauSpatial(context: Context, var x: Float, var y: Float) {
    private var spaceshipBitmap: Bitmap

    init {

        spaceshipBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.v)

        val newWidth = 250
        val newHeight = 250
        spaceshipBitmap = Bitmap.createScaledBitmap(spaceshipBitmap, newWidth, newHeight, false)
    }

    fun draw(canvas: Canvas) {

        canvas.drawBitmap(spaceshipBitmap, x - spaceshipBitmap.width / 2f, y - spaceshipBitmap.height / 2f, null)
    }
}
