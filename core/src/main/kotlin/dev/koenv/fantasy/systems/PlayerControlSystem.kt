package dev.koenv.fantasy.systems

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import dev.koenv.fantasy.components.PlayerComponent
import dev.koenv.fantasy.components.VelocityComponent

@All(PlayerComponent::class, VelocityComponent::class)
class PlayerControlSystem : BaseSystem() {
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun processSystem() {
        val entities = world.aspectSubscriptionManager.get(
            Aspect.all(
                PlayerComponent::class.java,
                VelocityComponent::class.java
            )
        ).entities
        val entityIds = entities.data
        for (i in 0 until entities.size()) {
            val entityId = entityIds[i]
            val velocity = velocityMapper.get(entityId)
            velocity.x = 0f
            velocity.y = 0f
            if (Gdx.input.isKeyPressed(Input.Keys.W)) velocity.y = 200f
            if (Gdx.input.isKeyPressed(Input.Keys.S)) velocity.y = -200f
            if (Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x = -200f
            if (Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x = 200f
        }
    }
}
