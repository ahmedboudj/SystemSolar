package com.example.solarsystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import kotlin.math.pow
import kotlin.random.Random

class AlienSolarSystem(context: Context) : SurfaceView(context), Runnable {

    private var planetInfoDisplayed = false
    private var thread: Thread? = null
    private var isRunning = false
    private var surfaceHolder: SurfaceHolder = holder
    private val planets: MutableList<AstreCeleste> = mutableListOf()
    private lateinit var ship: VaisseauSpatial
    private var selectedPlanet: AstreCeleste? = null

    init {
        surfaceHolder.addCallback(object : SurfaceHolder.Callback {

            override fun surfaceCreated(holder: SurfaceHolder) {
                isRunning = true
                thread = Thread(this@AlienSolarSystem)
                thread?.start()

                val width = width
                val height = height

                val planetImages = arrayOf(
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.jupiter), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.lune), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.mars), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.mars1), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.mercure), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.nasa), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.neptune), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.saturn1), 350, 350),
                    resizeBitmap(BitmapFactory.decodeResource(resources, R.drawable.terre), 350, 350)
                )

                for (i in 0 until 10) {
                    val inhabited = Random.nextBoolean()
                    val inhabitantsCount = if (inhabited) Random.nextInt(100000, 10000000) else 0
                    val planetImage = planetImages[i % planetImages.size] // Pour répéter les images si nécessaire
                    val planet = AstreCeleste(context, inhabited, inhabitantsCount, planetImage)
                    planet.x = Random.nextInt(0, width - planet.radius * 2).toFloat()
                    planet.y = Random.nextInt(0, height - planet.radius * 2).toFloat()
                    planets.add(planet)
                }

                ship = VaisseauSpatial(context, width / 2f, height / 2f)
            }


            private fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
                return Bitmap.createScaledBitmap(bitmap, width, height, true)
            }


            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                var retry = true
                isRunning = false
                while (retry) {
                    try {
                        thread?.join()
                        retry = false
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    override fun run() {
        while (isRunning) {
            if (surfaceHolder.surface.isValid) {
                val canvas = surfaceHolder.lockCanvas()

                val background = BitmapFactory.decodeResource(resources, R.drawable.espace)
                canvas.drawBitmap(background, 0f, 0f, null)

                for (planet in planets) {
                    planet.draw(canvas)
                }

                if (::ship.isInitialized) { // Vérifie si ship est initialisé avant de l'utiliser
                    ship.draw(canvas)
                } else {
                    // Si ship n'est pas initialisé, ne le dessinez pas ou effectuez d'autres actions nécessaires
                    // Vous pouvez également ignorer l'itération si ship n'est pas initialisé ou l'initialiser ici si c'est approprié
                }
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }



    fun pause() {
        isRunning = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        isRunning = true
        thread = Thread(this)
        thread?.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                planetInfoDisplayed = false

                for (planet in planets) {
                    val distance = distanceBetweenPoints(event.x, event.y, planet.x, planet.y)
                    if (distance <= planet.radius) {
                        selectedPlanet = planet
                        showPlanetInfo(planet)
                        break
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (::ship.isInitialized) { // Vérifie si ship est initialisé avant de l'utiliser
                    ship.x = event.x
                    ship.y = event.y

                    for (planet in planets) {
                        val distance = distanceBetweenPoints(ship.x, ship.y, planet.x, planet.y)
                        if (distance <= planet.radius && !planet.infoDisplayed) {
                            showPlanetInfo(planet)
                            planet.infoDisplayed = true
                            break
                        }
                    }
                }
            }
        }
        return true
    }



    private fun distanceBetweenPoints(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return kotlin.math.sqrt((x2 - x1).toDouble().pow(2) + (y2 - y1).toDouble().pow(2)).toFloat()
    }

    private fun showPlanetInfo(planet: AstreCeleste) {
        val info = if (planet.inhabited) {
            " Habité ;) \nNombre d'habitants : ${planet.inhabitantsCount}"
        } else {
            " Non habité :("
        }
        Toast.makeText(context, "Planète: $info", Toast.LENGTH_SHORT).show()
    }
}
