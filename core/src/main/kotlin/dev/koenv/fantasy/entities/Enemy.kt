package dev.koenv.fantasy.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

class Enemy(position: Vector2) : GameObject(position) {
    override fun render(shapeRenderer: ShapeRenderer) {
        shapeRenderer.setColor(1f, 0f, 0f, 1f) // Set color to red
        shapeRenderer.circle(position.x, position.y, 20f) // Draw enemy as a circle
    }
}
