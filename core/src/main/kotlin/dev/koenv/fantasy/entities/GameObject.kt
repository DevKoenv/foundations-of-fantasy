package dev.koenv.fantasy.entities

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

open class GameObject(val position: Vector2 = Vector2()) {
    open fun render(shapeRenderer: ShapeRenderer) {
        // Default render method, can be overridden by subclasses
    }
}
