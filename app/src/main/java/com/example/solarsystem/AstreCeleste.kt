package com.example.solarsystem


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas

class AstreCeleste(context: Context, val inhabited: Boolean, val inhabitantsCount: Int = 0, val image: Bitmap) {
    val radius = 100
    var x = 0f
    var y = 0f
    var infoDisplayed = false

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x - radius, y - radius, null)
    }

    fun setColor(white: Int) {

    }
}
