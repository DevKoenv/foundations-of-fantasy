package dev.koenv.fantasy.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

class Player(position: Vector2 = Vector2()) : GameObject(position) {
    override fun render(shapeRenderer: ShapeRenderer) {
        shapeRenderer.setColor(0f, 1f, 0f, 1f) // Set color to green
        shapeRenderer.circle(position.x, position.y, 25f) // Draw player as a circle
    }

    fun render(batch: SpriteBatch) {
        // Placeholder for rendering player with texture/animation
        // batch.draw(texture, position.x, position.y)
    }
}
