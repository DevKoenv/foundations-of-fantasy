package dev.koenv.fantasy.systems

import com.artemis.Aspect
import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.artemis.annotations.All
import dev.koenv.fantasy.components.PositionComponent
import dev.koenv.fantasy.components.VelocityComponent

@All(PositionComponent::class, VelocityComponent::class)
class MovementSystem : BaseSystem() {
    private lateinit var positionMapper: ComponentMapper<PositionComponent>
    private lateinit var velocityMapper: ComponentMapper<VelocityComponent>

    override fun processSystem() {
        val entities = world.aspectSubscriptionManager.get(
            Aspect.all(
                PositionComponent::class.java,
                VelocityComponent::class.java
            )
        ).entities
        val entityIds = entities.data
        for (i in 0 until entities.size()) {
            val entityId = entityIds[i]
            val position = positionMapper.get(entityId)
            val velocity = velocityMapper.get(entityId)
            position.x += velocity.x * world.delta
            position.y += velocity.y * world.delta
        }
    }
}
