package dev.koenv.fantasy.worlds

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import dev.koenv.fantasy.entities.Enemy
import dev.koenv.fantasy.entities.GameObject
import kotlin.random.Random

class World(private val border: Float = 5000f, seed: Long) {
    private val objects = mutableListOf<GameObject>()
    private val random = Random(seed)

    init {
        generateWorld()
    }

    private fun generateWorld() {
        for (i in 0 until 1000) {
            val x = random.nextFloat() * border * 2 - border
            val y = random.nextFloat() * border * 2 - border
            objects.add(Enemy(Vector2(x, y)))
        }
    }

    fun render(shapeRenderer: ShapeRenderer, camera: OrthographicCamera) {
        val cameraX = camera.position.x
        val cameraY = camera.position.y
        val viewportWidth = camera.viewportWidth * camera.zoom
        val viewportHeight = camera.viewportHeight * camera.zoom
        val buffer = 100f // Buffer zone around the viewport

        objects.forEach { obj ->
            val relativeX = obj.position.x - cameraX + viewportWidth / 2
            val relativeY = obj.position.y - cameraY + viewportHeight / 2

            if (relativeX in -viewportWidth - buffer..viewportWidth + buffer && relativeY in -viewportHeight - buffer..viewportHeight + buffer) {
                obj.render(shapeRenderer)
            }
        }
    }
}
